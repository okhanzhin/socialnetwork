<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Search"/>
</jsp:include>

<%@include file="templates/navbar.jsp" %>

<div class="container mb-3" id="content">
    <div class="row">

        <jsp:include page="templates/profile-sidebar.jsp">
            <jsp:param name="userId" value="${sessionScope.homeAccountId}"/>
            <jsp:param name="picAttached" value="${sessionScope.homeAccount.picAttached}"/>
        </jsp:include>

        <div id="hiddenFields">
            <input type="hidden" id="accountNumOfPages" value="${accountNumOfPages}">
            <input type="hidden" id="commNumOfPages" value="${commNumOfPages}">
        </div>

        <div class="col-9">
            <main>
                <div class="card card-alter">
                    <h5 class="card-header mb-3">Search Results</h5>

                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link ${activeTab == 'accounts' ? 'active' : ''}" id="account-tab"
                               data-toggle="tab" href="#accounts" role="tab"
                               aria-controls="home" aria-selected="true">Accounts</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${activeTab == 'communities' ? 'active' : ''}" id="community-tab"
                               data-toggle="tab" href="#communities" role="tab" aria-controls="profile"
                               aria-selected="false">Communities</a>
                        </li>
                    </ul>

                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade ${activeTab == 'accounts' ? 'show active' : ''}" id="accounts"
                             role="tabpanel" aria-labelledby="accounts-tab">
                            <c:if test="${empty accounts}">
                                <div class="chat-body text-center">
                                    <h3 class="empty-list-message">No Search Results</h3>
                                </div>
                            </c:if>
                            <div id="accountsResults">
                                <c:forEach var="account" items="${accounts}">
                                    <div class="card-body item-list">
                                        <div class="card card-inner-element">
                                            <div class="card-body d-flex">
                                                <div class="small-pic-container">
                                                    <c:choose>
                                                        <c:when test="${account.picAttached}">
                                                            <div style="background-image: url(${pageContext.request.contextPath}/displayPicture?accId=${account.accountID});"
                                                                 class="small-pic-container img-thumbnail rounded small-pic"></div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div style="background-image: url(${pageContext.request.contextPath}/resources/img/default_user.png);"
                                                                 class="small-pic-container img-thumbnail rounded small-pic"></div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="ml-3">
                                                    <h4 class="mb-3"><a
                                                            href="<c:url value="/account/${account.accountID}"/>">
                                                        <c:out value="${account.name} ${account.surname}"/></a>
                                                    </h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="tab-pane fade ${activeTab == 'communities' ? 'show active' : ''}" id="communities"
                             role="tabpanel" aria-labelledby="communities-tab">

                            <c:if test="${empty communities}">
                                <div class="chat-body text-center">
                                    <h3 class="empty-list-message">No Search Results</h3>
                                </div>
                            </c:if>

                            <div id="communitiesResults">
                                <c:forEach var="community" items="${communities}">
                                    <div class="card-body item-list">
                                        <div class="card card-inner-element">
                                            <div class="card-body d-flex">
                                                <div class="small-pic-container">
                                                    <c:choose>
                                                        <c:when test="${community.picAttached}">
                                                            <div style="background-image: url(${pageContext.request.contextPath}/displayPicture?commId=${community.commID});"
                                                                 class="small-pic-container img-thumbnail rounded small-pic"></div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div style="background-image: url(${pageContext.request.contextPath}/resources/img/default_community.png);"
                                                                 class="small-pic-container img-thumbnail rounded small-pic"></div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="ml-3">
                                                    <h4 class="mb-3"><a
                                                            href="<c:url value="/community/${community.commID}"/>">
                                                        <c:out value="${community.communityName}"/></a>
                                                    </h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>

<script id="template-account" type="x-tmpl-mustache">
                                    <div class="card-body item-list">
                                        <div class="card card-inner-element">
                                            <div class="card-body d-flex">
                                                <div class="small-pic-container">
                                                    <div style="background-image: url({{{picturePath}}});"
                                                     class="small-pic-container img-thumbnail rounded small-pic"></div>
                                                </div>
                                                <div class="ml-3">
                                                    <h4 class="mb-3"><a
                                                            href="/account/{{accountID}}">
                                                        {{name}} {{surname}}</a>
                                                    </h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

</script>

<script id="template-community" type="x-tmpl-mustache">
                                    <div class="card-body item-list">
                                        <div class="card card-inner-element">
                                            <div class="card-body d-flex">
                                                <div class="small-pic-container">
                                                    <div style="background-image: url({{{picturePath}}});"
                                                     class="small-pic-container img-thumbnail rounded small-pic"></div>
                                                </div>
                                                <div class="ml-3">
                                                    <h4 class="mb-3"><a
                                                            href="/community/{{commID}}">
                                                        {{communityName}}</a>
                                                    </h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

</script>

<%@include file="templates/footer.jsp" %>

<script src="https://unpkg.com/mustache@latest"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/search/main-search.js"></script>