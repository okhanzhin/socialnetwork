const ACCOUNT_TYPE = 'accounts';
const COMMUNITY_TYPE = 'communities';
console.log('Start script');

$(function () {
    console.log("start main func");
    let url = new URL(document.URL);
    let searchValue = url.searchParams.get('value');
    let currentPage = url.searchParams.get('currentPage');
    let activeTypeId = $('.nav-item > .active').attr('href');

    console.log(activeTypeId);

    showPaginationPanel(activeTypeId, searchValue, currentPage);
    bindPaginationButtons(currentPage);

    let $dataToggle = $('a[data-toggle="tab"]');
    console.log($dataToggle);

    $dataToggle.on('click', function (e) {
        if (!$(this).hasClass('active')) {
            processPage.call(this);
        }
    })

    function processPage() {
        activeTypeId = $(this).attr('href');
        console.log(activeTypeId);

        bindPaginationButtons(currentPage);
    }


    function bindPaginationButtons(currentPage) {
        showPaginationPanel(activeTypeId, searchValue, currentPage);
        console.log(activeTypeId + ' ' + searchValue + ' ' + currentPage);

        $('.page-item:not(.active) > .page-link').on('click', function (e) {
            console.log("Click");
            if ($(this).text() === 'Previous') {
                --currentPage;
            } else if ($(this).text() === 'Next') {
                ++currentPage;
            } else {
                currentPage = $(this).text();
            }

            showPage(currentPage);
        })
    }

    function showPage(currentPage) {
        let searchEntitiesType = activeTypeId.charAt(1).toLocaleUpperCase() +
            activeTypeId.substr(2, activeTypeId.length);
        console.log(searchEntitiesType);
        console.log(searchValue);

        $.ajax({
                type: 'get',
                url: '/search' + searchEntitiesType,
                data: {
                    currentPage: currentPage,
                    value: searchValue
                },
                dataType: 'json',
                success: function (data) {
                    console.log('Ajax done');
                    console.log(data)
                    buildSearchResults(data, activeTypeId);
                    showPaginationPanel(activeTypeId, searchValue, currentPage);
                    bindPaginationButtons(currentPage);
                }
            }
        )

        function buildSearchResults(data, activeTypeId) {
            let $searchResults = $(activeTypeId + 'Results').html('');

            $.each(data, function (i, jsonData) {
                    console.log(jsonData.picAttached);

                    let template;
                    let resultEntry;
                    let picturePath;

                    if (activeTypeId === '#accounts') {
                        template = $('#template-account').html();

                        if (jsonData.picAttached === true) {
                            picturePath = '/displayPicture?accId=' + jsonData.accountID;
                            resultEntry = Mustache.render(template, {
                                picturePath: picturePath, accountID: jsonData.accountID,
                                name: jsonData.name, surname: jsonData.surname
                            });
                        } else {
                            picturePath = '/resources/img/default_user.png';
                            resultEntry = Mustache.render(template, {
                                picturePath: picturePath, accountID: jsonData.accountID,
                                name: jsonData.name, surname: jsonData.surname
                            });
                        }
                    } else {
                        template = $('#template-community').html();

                        if (jsonData.picAttached === true) {
                            picturePath = '/displayPicture?commId=' + jsonData.commID;
                            resultEntry = Mustache.render(template, {
                                picturePath: picturePath, commID: jsonData.commID,
                                communityName: jsonData.communityName
                            });
                        } else {
                            picturePath = '/resources/img/default_community.png';
                            resultEntry = Mustache.render(template, {
                                picturePath: picturePath, commID: jsonData.commID,
                                communityName: jsonData.communityName
                            });
                        }
                    }

                    console.log(resultEntry);

                    let $paginationPanel = $(activeTypeId + 'Results' + ' > #paginationPanel');
                    console.log($paginationPanel);

                    $searchResults.append(resultEntry);
                }
            );
        }
    }

    function showPaginationPanel(activeTypeId, searchValue, currentPage) {
        let numOfPages = setNumOfPages(activeTypeId);
        let resultsType = activeTypeId + 'Results';

        setSearchEntitiesType(activeTypeId);

        let paginationPanel = '<nav aria-label="..." id="paginationPanel">\n' +
            '<ul class="pagination">';

        if (currentPage > 1) {
            paginationPanel = paginationPanel + ' <li class="page-item">\n' +
                '<a class="page-link" href="#">Previous</a>' +
                '</li>';
        }

        for (let i = 1; i <= numOfPages; i++) {
            if (numOfPages > 1) {
                if (i == currentPage) {
                    paginationPanel = paginationPanel + '<li class="page-item active">' +
                        '<a class="page-link" href="#">' + i + ' <span class="sr-only">(current)</span></a></li>';
                } else {
                    paginationPanel = paginationPanel + '<li class="page-item"><a class="page-link"' +
                        ' href="#">' + i + "</a>";
                }
            }
        }

        if (currentPage < numOfPages) {
            paginationPanel = paginationPanel + ' <li class="page-item">\n' +
                '<a class="page-link" href="#">Next</a>' +
                '</li>';
        }

        paginationPanel = paginationPanel + '</ul></nav>';

        console.log(activeTypeId)

        $(resultsType + ' > #paginationPanel').remove();
        $(resultsType).append(paginationPanel);
    }

    function setSearchEntitiesType(activeTypeId) {
        if (activeTypeId === "#accounts") {
            return ACCOUNT_TYPE.charAt(0).toLocaleUpperCase() +
                ACCOUNT_TYPE.substr(1, ACCOUNT_TYPE.length);
        } else {
            return COMMUNITY_TYPE.charAt(0).toLocaleUpperCase() +
                COMMUNITY_TYPE.substr(1, COMMUNITY_TYPE.length);
        }
    }

    function setNumOfPages(activeTypeId) {
        if (activeTypeId === "#accounts") {
            return $('#accountNumOfPages').val();
        } else {
            return $('#commNumOfPages').val();
        }
    }
});