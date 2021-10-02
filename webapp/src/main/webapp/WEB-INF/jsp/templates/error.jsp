<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/templates/head.jsp">
    <jsp:param name="pageTitle" value="Profile"/>
</jsp:include>

<%@include file="/WEB-INF/jsp/templates/navbar.jsp" %>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-6">
            <div class="card p-4">
                <h3 class="row justify-content-center align-items-center">Oops, something went wrong</h3>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/templates/footer.jsp" %>
