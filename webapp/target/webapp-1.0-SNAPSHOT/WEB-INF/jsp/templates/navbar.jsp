<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
<%--        <img src="${pageContext.request.contextPath}/resources/img/user.png" width="30" height="30" alt="">--%>
        <a class="navbar-brand" href="#">Social Network</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active" href="#">Home <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="#">Friends</a>
                <a class="nav-item nav-link" href="#">Messages</a>
                <a class="nav-item nav-link" href="#">Groups</a>
            </div>
        </div>
        <form class="form-inline" action="${pageContext.request.contextPath}/search" method="get">
            <input type="hidden" name="currentPage" value="1">
            <input class="form-control mr-sm-2" type="search" name="value" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </nav>
</head>
</html>
