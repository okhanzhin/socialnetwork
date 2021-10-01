<%--suppress ALL --%>
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
            <jsp:param name="isUnpointed" value="${isUnpointed}"/>
            <jsp:param name="isBlocked" value="${isBlocked}"/>
        </jsp:include>

        <div class="col-9">
            <main>
                <div class="card">
                    <h5 class="card-header mb-3">Communities</h5>

                    <c:if test="${empty commList}">
                        <div class="chat-body text-center">
                            <h3 class="empty-list-message">No communities yet</h3>
                        </div>
                    </c:if>

                    <c:forEach var="community" items="${commList}">
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
                                                href="<c:url value="/community/${community.commID}"/> ">
                                            <c:out value="${community.communityName}"/></a>
                                        </h4>
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

<%@include file="templates/footer.jsp"%>