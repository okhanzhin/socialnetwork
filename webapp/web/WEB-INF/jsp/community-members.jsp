<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Community"/>
</jsp:include>

<%@include file="templates/navbar.jsp" %>

<div class="container mb-3" id="content">
    <div class="row">

        <jsp:include page="templates/community-sidebar.jsp">
            <jsp:param name="communityId" value="${sessionScope.activeCommunity.commID}"/>
            <jsp:param name="picAttached" value="${sessionScope.activeCommunity.picAttached}"/>
        </jsp:include>

        <c:set var="community" scope="page" value="${sessionScope.activeCommunity}"/>
        <c:set var="isAbleToModify" scope="page" value="${sessionScope.commPermissions.get('isAbleToModify')}"/>
        <c:set var="visitorID" scope="page" value="${sessionScope.homeAccountId}"/>

        <div class="col-9">
            <main>
                <div class="card">
                    <h5 class="card-header mb-3">Members</h5>

                    <c:forEach var="account" items="${accountsList}">
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
                                                href="<c:url value="/account/${account.accountID}"/> ">
                                            <c:out value="${account.name} ${account.surname}"/></a>
                                        </h4>

                                        <c:if test="${isAbleToModify && account.accountID != visitorID}">
                                            <c:set var="isModerator" scope="page" value="false"/>
                                            <c:if test="${moderatorsList.size() != 0}">
                                                <c:forEach var="moderator" items="${moderatorsList}">
                                                    <c:if test="${moderator.accountID == account.accountID}">
                                                        <c:set var="isModerator" scope="page" value="true"/>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>

                                            <c:if test="${account.accountID != ownerId}">
                                                <c:if test="${!isModerator}">
                                                    <a class="btn btn-info btn-sm"
                                                       href="<c:url value="/community/${community.commID}/makeModerator?accountId=${account.accountID}"/>">Make
                                                        Moderator</a>
                                                </c:if>

                                                <c:if test="${isModerator}">
                                                    <a class="btn btn-info btn-sm"
                                                       href="<c:url value="/community/${community.commID}/makeUser?accountId=${account.accountID}"/>">Make
                                                        User</a>
                                                </c:if>

                                                <c:if test="${!isModerator}">
                                                    <a class="btn btn-delete text-nowrap ml-auto btn-sm"
                                                       href="<c:url value="/community/${community.commID}/deleteUser?accountId=${account.accountID}"/>">Delete</a>
                                                </c:if>
                                            </c:if>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </main>
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp" %>

