<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="navbar-dark bg-dark mb-3">
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand" href="#">Sketch Network</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                    aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-item nav-link active"
                       href="<c:url value="/account/${sessionScope.homeAccountId}"/>">Home <span class="sr-only">(current)</span></a>
                    <a class="nav-item nav-link" href="<c:url value="/account/${sessionScope.homeAccountId}/friends"/>">Friends</a>
                    <a class="nav-item nav-link" href="<c:url value="/account/messenger"/>">Messages</a>
                    <a class="nav-item nav-link" href="<c:url value="/account/${sessionScope.homeAccountId}/communities"/>">Communities</a>
                </div>
            </div>
            <form class="form-inline" method="GET" action="${pageContext.request.contextPath}/search" autocomplete="off">
                <input class="form-control mr-sm-2" type="search" name="value" id="search-value" placeholder="Search" aria-label="Search">
                <input type="hidden" name="currentPage" value="1">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </nav>
    </div>
</header>
