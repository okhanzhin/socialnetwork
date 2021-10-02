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
                <div class="card">
                    <div class="card-body p-4">
                        <h5 class="chat-interlocutor-label text-center mt-1">
                            <c:out value="${chat.interlocutor.name} ${chat.interlocutor.surname}"/>
                        </h5>

                        <div class="card mb-3">
                            <div class="card-body chat-window">
                                <div id="hiddenFields">
                                    <input type="hidden" id="chatRoomId" value="${sessionScope.chatRoom.chatRoomID}">
                                    <input type="hidden" id="homeAccountId" value="${sessionScope.homeAccountId}">
                                    <input type="hidden" id="interlocutorId" value="${chat.interlocutor.accountID}">
                                </div>

                                <c:if test="${empty chat.messages}">
                                    <div class="chat-body text-center">
                                        <h3 class="empty-list-message">Start messaging</h3>
                                    </div>
                                </c:if>

                                <c:forEach var="message" items="${chat.messages}">
                                    <c:choose>
                                        <c:when test="${message.source.accountID eq sessionScope.homeAccountId}">
                                            <div class="chat-message text-right">
                                                <div class="chat-message-content">
                                                    <div class="alert d-inline-block alert-info" role="alert">
                                                        <c:out value="${message.content}"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="chat-message">
                                                <div class="chat-message-content">
                                                    <div class="alert d-inline-block alert-success" role="alert">
                                                        <c:out value="${message.content}"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="card mb-3">
                            <div class="card-body card-alter">
                                <div class="input-community mb-3">
                                        <textarea class="form-control" id="message-input" name="content"
                                                  oninput='this.style.height = "";this.style.height = this.scrollHeight + "px"'
                                                  onfocusout="this.value == '' ? this.style.height='' : this.style.height=this.scrollHeight+'px'"
                                                  rows="1"></textarea>
                                    <div class="d-flex align-items-end ml-3">
                                        <button class="btn btn-info ml-auto mt-2" id="button-send">
                                            Send
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>


<script id="sent-template" type="x-tmpl-mustache">
                                        <div class="chat-message text-right">
                                            <div class="chat-message-content">
                                                <div class="alert d-inline-block alert-info" role="alert">
                                                    {{messageContent}}
                                                </div>
                                            </div>
                                        </div>

</script>

<script id="reply-template" type="x-tmpl-mustache">
                                        <div class="chat-message">
                                            <div class="chat-message-content">
                                                <div class="alert d-inline-block alert-success" role="alert">
                                                    {{messageContent}}
                                                </div>
                                            </div>
                                        </div>

</script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/webjars/sockjs-client/1.1.1/sockjs.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/webjars/stomp-websocket/2.3.4/stomp.min.js"></script>

<%@include file="templates/footer.jsp" %>

<script src="https://unpkg.com/mustache@latest"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/scrollAlwaysBottom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/websocket.js"></script>
