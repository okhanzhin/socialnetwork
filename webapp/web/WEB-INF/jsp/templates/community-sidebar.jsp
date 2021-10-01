<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-3">
    <aside>
        <div class="card p-4 mb-3">
            <c:set var="community" scope="page" value="${sessionScope.activeCommunity}"/>
            <c:set var="isAbleToModify" scope="page" value="${sessionScope.commPermissions.get('isAbleToModify')}"/>
            <c:set var="isSubscribed" scope="page" value="${sessionScope.commPermissions.get('isSubscribed')}"/>
            <c:set var="isMember" scope="page" value="${sessionScope.commPermissions.get('isMember')}"/>
            <c:set var="visitorStatus" scope="page" value="${sessionScope.commPermissions.get('visitorStatus')}"/>

            <c:choose>
                <c:when test="${param.picAttached}">
                    <div class="avatar rounded mb-3" style="background-image:
                            url(${pageContext.request.contextPath}/displayPicture?commId=${param.communityId}"></div>
                </c:when>
                <c:otherwise>
                    <div class="avatar rounded mb-3" style="background-image:
                            url(<c:url value="/resources/img/default_community.png"/>)"></div>
                </c:otherwise>
            </c:choose>

            <div class="card-body p-0">
                <c:if test="${isAbleToModify}">
                    <c:if test="${param.pageTitle ne 'Edit'}">
                        <a href="<c:url value="/community/${community.commID}/update"/>"
                           class="btn btn-info btn-block">Edit</a>
                    </c:if>
                </c:if>

                <c:if test="${!isMember && !isSubscribed}">
                    <a href="<c:url value="/community/${community.commID}/follow"/>"
                       class="btn btn-info btn-block">Follow</a>
                </c:if>

                <c:if test="${isSubscribed}">
                    <a href="<c:url value="/community/${community.commID}/unfollow"/>"
                       class="btn btn-info btn-block">Unfollow</a>
                </c:if>

                <c:if test="${isMember}">
                    <c:if test="${visitorStatus > 0}">
                        <a href="<c:url value="/community/${community.commID}/deleteUser"/>"
                           class="btn btn-warning btn-block">Leave Group</a>
                    </c:if>
                </c:if>
            </div>
        </div>
        <div class="card p-4">
            <div class="card-body p-0">
                <a href="<c:url value="/community/${community.commID}/members"/>"
                   class="btn btn-info btn-block">Members</a>

                <c:if test="${isAbleToModify}">
                    <a href="<c:url value="/community/${community.commID}/requests"/>"
                       class="btn btn-info btn-block">Requests</a>
                </c:if>
            </div>
        </div>
    </aside>
</div>
