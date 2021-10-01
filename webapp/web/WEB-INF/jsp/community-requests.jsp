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

        <div class="col-9">
            <main>
                <div class="card">
                    <h5 class="card-header mb-3">Requests</h5>

                    <c:if test="${empty unconfirmedList}">
                        <div class="chat-body text-center">
                            <h3 class="empty-list-message">No requests yet</h3>
                        </div>
                    </c:if>

                    <c:forEach var="unconfirmedAccount" items="${unconfirmedList}">
                        <div class="card-body item-list">
                            <div class="card card-inner-element">
                                <div class="card-body d-flex">
                                    <div class="small-pic-container">
                                        <c:choose>
                                            <c:when test="${unconfirmedAccount.picAttached}">
                                                <div style="background-image: url(${pageContext.request.contextPath}/displayPicture?accId=${unconfirmedAccount.accountID});"
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
                                                href="<c:url value="/account/${unconfirmedAccount.accountID}"/> ">
                                            <c:out value="${unconfirmedAccount.name} ${unconfirmedAccount.surname}"/></a>
                                        </h4>

                                        <a class="btn btn-info btn-sm"
                                           href="<c:url value="/community/${community.commID}/acceptRequest?accountId=${unconfirmedAccount.accountID}"/>">Accept</a>
                                        <a class="btn btn-warning btn-sm"
                                           href="<c:url value="/community/${community.commID}/declineRequest?accountId=${unconfirmedAccount.accountID}"/>">Decline</a>
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