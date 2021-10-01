<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/templates/head.jsp">
    <jsp:param name="pageTitle" value="Profile"/>
</jsp:include>

<%@include file="/WEB-INF/jsp/templates/navbar.jsp" %>

<div class="container mb-3" id="content">
    <div class="row">

        <jsp:include page="/WEB-INF/jsp/templates/profile-sidebar.jsp">
            <jsp:param name="userId" value="${sessionScope.homeAccountId}"/>
            <jsp:param name="picAttached" value="${sessionScope.homeAccount.picAttached}"/>
        </jsp:include>

        <div class="col-9">
            <main>
                <div class="card">
                    <h5 class="card-header card-alter mb-3">Search Results</h5>
                    <div class="chat-body text-center">
                        <h3 class="empty-list-message">No Search Results</h3>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/templates/footer.jsp" %>