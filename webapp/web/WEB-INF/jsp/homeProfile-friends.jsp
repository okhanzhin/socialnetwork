<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Profile"/>
</jsp:include>

<%@include file="templates/navbar.jsp" %>

<div class="container mb-3" id="content">
    <div class="row">

        <jsp:include page="templates/profile-sidebar.jsp">
            <jsp:param name="userId" value="${sessionScope.homeAccountId}"/>
            <jsp:param name="picAttached" value="${sessionScope.homeAccount.picAttached}"/>
        </jsp:include>

        <div class="col-9">
            <main>
                <div class="card card-alter">
                    <h5 class="card-header mb-3">Friends</h5>

                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <a class="nav-link active" id="friends-tab" data-toggle="tab" href="#friends" role="tab"
                               aria-controls="friends" aria-selected="true">Friends</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link" id="pending-tab" data-toggle="tab" href="#pending" role="tab"
                               aria-controls="pending" aria-selected="false">Pending</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link" id="outgoing-tab" data-toggle="tab" href="#outgoing" role="tab"
                               aria-controls="outgoing" aria-selected="false">Outgoing</a>
                        </li>
                    </ul>

                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="friends" role="tabpanel"
                             aria-labelledby="friends-tab">
                            <c:if test="${empty friends}">
                                <div class="chat-body text-center">
                                    <h3 class="empty-list-message">No friends yet</h3>
                                </div>
                            </c:if>
                            <c:forEach var="friend" items="${friends}">
                                <div class="card-body item-list">
                                    <div class="card card-inner-element">
                                        <div class="card-body d-flex">
                                            <c:choose>
                                                <c:when test="${friend.picAttached}">
                                                    <div style="background-image: url(${pageContext.request.contextPath}/displayPicture?accId=${friend.accountID});"
                                                         class="small-pic-container img-thumbnail rounded small-pic"></div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div style="background-image: url(${pageContext.request.contextPath}/resources/img/default_user.png);"
                                                         class="small-pic-container img-thumbnail rounded small-pic"></div>
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="ml-3">
                                                <h4 class="mb-3"><a
                                                        href="<c:url value="/account/${friend.accountID}"/>">
                                                    <c:out value="${friend.name} ${friend.surname}"/></a>
                                                </h4>

                                                <c:forEach var="request" items="${acceptedRequests}">
                                                    <c:if test="${request.source.accountID == friend.accountID ||
                                                                  request.accountTarget.accountID == friend.accountID}">
                                                        <c:set var="creatorId" scope="page"
                                                               value="${request.source.accountID}"/>
                                                        <c:set var="recipientId" scope="page"
                                                               value="${request.accountTarget.accountID}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <a class="btn btn-info btn-sm"
                                                   href="<c:url value="/account/unfriend?creatorId=${creatorId}&recipientId=${recipientId}"/>">
                                                    Unfriend</a>
                                                <a class="btn btn-warning btn-sm"
                                                   href="<c:url value="/account/${friend.accountID}/dialogue"/>">
                                                    Write message</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class=" tab-pane fade" id="pending" role="tabpanel" aria-labelledby="pending-tab">

                            <c:if test="${empty pendingAccounts}">
                                <div class="chat-body text-center">
                                    <h3 class="empty-list-message">No pending requests</h3>
                                </div>
                            </c:if>

                            <c:forEach var="pending" items="${pendingAccounts}">
                                <div class="card-body item-list">
                                    <div class="card card-inner-element">
                                        <div class="card-body d-flex">
                                            <div class="small-pic-container">
                                                <c:choose>
                                                    <c:when test="${pending.picAttached}">
                                                        <div style="background-image: url(${pageContext.request.contextPath}/displayPicture?accId=${pending.accountID});"
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
                                                        href="<c:url value="/account/${pending.accountID}"/>">
                                                    <c:out value="${pending.name} ${pending.surname}"/></a>
                                                </h4>
                                                <a class="btn btn-info btn-sm"
                                                   href="<c:url value="/account/acceptPendingRequest?accountId=${pending.accountID}"/>">
                                                    Accept</a>
                                                <a class="btn btn-warning btn-sm"
                                                   href="<c:url value="/account/declinePendingRequest?accountId=${pending.accountID}"/>">
                                                    Decline</a>
                                                <a class="btn btn-delete btn-sm"
                                                   href="<c:url value="/account/block?accountId=${pending.accountID}"/>">
                                                    Block</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class=" tab-pane fade" id="outgoing" role="tabpanel" aria-labelledby="outgoing-tab">

                            <c:if test="${empty outgoingAccounts}">
                                <div class="chat-body text-center">
                                    <h3 class="empty-list-message">No outgoing requests</h3>
                                </div>
                            </c:if>

                            <c:forEach var="outgoing" items="${outgoingAccounts}">
                                <div class="card-body item-list">
                                    <div class="card card-inner-element">
                                        <div class="card-body d-flex">
                                            <div class="small-pic-container">
                                                <c:choose>
                                                    <c:when test="${outgoing.picAttached}">
                                                        <div style="background-image: url(${pageContext.request.contextPath}/displayPicture?accId=${outgoing.accountID});"
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
                                                        href="<c:url value="/account/${outgoing.accountID}"/>">
                                                    <c:out value="${outgoing.name} ${outgoing.surname}"/></a>
                                                </h4>
                                                <a class="btn btn-delete btn-sm"
                                                   href="<c:url value="/account/block?accountId=${outgoing.accountID}"/>">
                                                    Block</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp" %>