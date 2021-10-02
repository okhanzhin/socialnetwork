const NO_RESULTS = "No Results";
const ERROR_MESSAGE = "An error occurred: the server isn't responding";

$(function () {
    $('#search-value').autocomplete({
        source: function (request, response) {
            console.log(request);
            console.log(response);
            $.ajax({
                url: '/search-ajax',
                data: {
                    value: request.term
                },
                type: 'get',
                dataType: 'json',
                success: function (data) {
                    response($.map(data, function (searchEntry, i) {
                        console.log(data.length)
                        return {
                            value: searchEntry.name,
                            url: searchEntry.entryUrl
                        };
                    }))
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(ERROR_MESSAGE);
                }
            });
        },
        minLength: 2,
        select: function (event, ui) {
            $(location).attr('href', ui.item.url)
        },
        response: function (event, ui) {
            if (!ui.content.length) {
                let noResult = {value: "", label: NO_RESULTS};
                ui.content.push(noResult);
            }
        }
    });
});
