<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
                <div class="card">
                    <h5 class="card-header mb-3">Messenger</h5>

                    <c:if test="${empty chatList}">
                        <div class="chat-body text-center">
                            <h3 class="empty-list-message">No dialogues yet</h3>
                        </div>
                    </c:if>

                    <c:forEach var="chat" items="${chatList}">
                        <div class="chat-body item-list">
                            <a href="<c:url value="/account/${chat.interlocutor.accountID}/dialogue"/>"
                               class="text-reset text-decoration-none card">
                                <div class="card-inner-element">
                                    <div class="card-body d-flex">
                                        <c:choose>
                                            <c:when test="${chat.interlocutor.picAttached}">
                                                <div style="background-image: url(${pageContext.request.contextPath}/displayPicture?accId=${chat.interlocutor.accountID});"
                                                     class="small-pic-container img-thumbnail rounded small-pic">
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div style="background-image: url(${pageContext.request.contextPath}/resources/img/default_user.png);"
                                                     class="small-pic-container img-thumbnail rounded small-pic">
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="ml-3">
                                            <h4 class="mb-3"><c:out
                                                    value="${chat.interlocutor.name} ${chat.interlocutor.surname}"/></h4>
                                            <c:out value="${fn:substring(chat.lastMessage.content, 0, 20)}..."/>
                                        </div>
                                    </div>
                                </div>
                            </a>
                            <a class="btn btn-delete btn-sm chat-delete"
                               href="<c:url value="/account/deleteDialog?chatRoomId=${chat.chatRoomID}"/>">
                                Delete</a>
                        </div>
                    </c:forEach>
                </div>
            </main>
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp" %>