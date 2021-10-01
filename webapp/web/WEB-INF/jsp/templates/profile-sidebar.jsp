<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-3">
    <aside>
        <div class="card p-4 mb-3">
            <c:choose>
                <c:when test="${param.picAttached}">
                    <div class="avatar rounded mb-3" style="background-image:
                            url(${pageContext.request.contextPath}/displayPicture?accId=${param.userId})"></div>
                </c:when>
                <c:otherwise>
                    <div class="avatar rounded mb-3" style="background-image:
                            url(<c:url value="/resources/img/default_user.png"/>)"></div>
                </c:otherwise>
            </c:choose>

            <c:if test="${sessionScope.homeAccountId == param.userId || sessionScope.homeAccount.role eq 'ADMIN'}">
                <div class="card-body p-0 mb-2">
                    <c:if test="${param.pageTitle ne 'Edit'}">
                        <c:choose>
                            <c:when test="${param.userId eq sessionScope.homeAccountId}">
                                <a href="<c:url value="/account/${param.userId}/update"/>"
                                   class="btn btn-info  btn-block">Edit</a>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${param.userRole eq 'USER'}">
                                <a href="<c:url value="/admin/${param.userId}/update"/>"
                                   class="btn btn-info  btn-block">Edit</a>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:if>

                    <c:if test="${sessionScope.homeAccountId == param.userId}">
                        <c:if test="${param.pageTitle ne 'Create Group'}">
                            <a href="<c:url value="/community/new"/>"
                               class="btn btn-info btn-block">Create Community</a>
                        </c:if>

                        <a href="<c:url value="/logout"/>" class="btn btn-warning btn-block">Log Out</a>
                    </c:if>
                </div>
            </c:if>

            <c:if test="${sessionScope.homeAccountId != param.userId && !param.isBlocked}">
                <div class="card-body p-0">
                    <a class="btn btn-info btn-block"
                       href="<c:url value="/account/${param.userId}/dialogue"/>">
                        Write message</a>

                    <c:if test="${param.isUnpointed}">
                        <a class="btn btn-info btn-block"
                           href="<c:url value="/account/${param.userId}/follow"/>">
                            Follow</a>
                    </c:if>
                </div>
            </c:if>
        </div>
        <div class="card p-4">
            <div class="card-body p-0">
                <c:if test="${sessionScope.homeAccountId == param.userId}">
                    <a href="<c:url value="/account/messenger"/>"
                       class="btn btn-info btn-block">Messenger</a>
                </c:if>

                <a href="<c:url value="/account/${param.userId}/friends"/>"
                   class="btn btn-info btn-block">Friends</a>

                <a href="<c:url value="/account/${param.userId}/communities"/>"
                   class="btn btn-info btn-block">Communities</a>
            </div>
        </div>
    </aside>
</div>