<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Profile"/>
</jsp:include>

<%@include file="templates/navbar.jsp" %>

<div class="container mb-3" id="content">
    <div class="row">

        <jsp:include page="templates/profile-sidebar.jsp">
            <jsp:param name="userId" value="${userAccount.accountID}"/>
            <jsp:param name="picAttached" value="${userAccount.picAttached}"/>
        </jsp:include>

        <div class="col-9">
            <main>
                <div class="card">
                    <h5 class="card-header mb-3">Friends</h5>

                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <a class="nav-link active" id="friends-tab" data-toggle="tab" href="#friends" role="tab"
                               aria-controls="friends" aria-selected="true">Friends</a>
                        </li>
                    </ul>

                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="friends" role="tabpanel" aria-labelledby="friends-tab">

                            <c:if test="${empty friends}">
                                <div class="chat-body text-center">
                                    <h3 class="empty-list-message">No friends yet</h3>
                                </div>
                            </c:if>

                            <c:forEach var="friend" items="${friends}">
                                <div class="card-body p-4">
                                    <div class="card mb-3">
                                        <div class="card-body d-flex">
                                            <div class="small-pic-container">
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
                                            </div>
                                            <div class="ml-3">
                                                <h4 class="mb-3"><a
                                                        href="<c:url value="/account/${friend.accountID}"/>">
                                                    <c:out value="${friend.name} ${friend.surname}"/></a>
                                                </h4>
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
