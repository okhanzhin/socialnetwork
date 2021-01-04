<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="templates/navbar.jsp" %>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <style>
        form {
            margin-bottom: 0
        }
    </style>

    <title>SearchResults</title>
</head>
<body>
<section>
    <div class="container">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link ${requestScope.activeTab == 'accounts' ? 'active' : ''}" id="home-tab"
                   data-toggle="tab" href="#accounts" role="tab"
                   aria-controls="home" aria-selected="true">Accounts</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${requestScope.activeTab == 'groups' ? 'active' : ''}" id="profile-tab"
                   data-toggle="tab" href="#groups" role="tab" aria-controls="profile"
                   aria-selected="false">Groups</a>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade ${requestScope.activeTab == 'accounts' ? 'show active' : ''}" id="accounts"
                 role="tabpanel" aria-labelledby="accounts-tab">

                <c:forEach var="account" items="${requestScope.accounts}">
                    <div class="group-item">
                        <div class="d-flex">
                            <h5 style="margin-bottom: .7em; margin-right: .5em; width: 70%"><a
                                    href="<c:url value="/account?id=${account.accountID}"/> ">
                                <c:out value="${account.name} ${account.surname}"/></a>
                            </h5>
                        </div>
                    </div>
                </c:forEach>

                <nav aria-label="...">
                    <ul class="pagination">
                        <c:if test="${requestScope.currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="<c:url value="/search?currentPage=${requestScope.currentPage-1}&value=${requestScope.value}&tab=accounts"/>">Previous</a>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${requestScope.accountNumOfPages}" var="i">
                            <c:choose>
                                <c:when test="${requestScope.currentPage eq i}">
                                    <li class="page-item active">
                                        <a class="page-link" href="#">${i} <span
                                                class="sr-only">(current)</span></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="<c:url value="/search?currentPage=${i}&value=${requestScope.value}&tab=accounts"/>">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${requestScope.currentPage lt requestScope.accountNumOfPages}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="<c:url value="/search?currentPage=${requestScope.currentPage+1}&value=${requestScope.value}&tab=accounts"/>">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>

            <div class="tab-pane fade ${requestScope.activeTab == 'groups' ? 'show active' : ''}" id="groups"
                 role="tabpanel" aria-labelledby="groups-tab">
                <c:forEach var="group" items="${requestScope.groups}">
                    <div class="group-item">
                        <div class="d-flex">
                            <h5 style="margin-bottom: .7em; margin-right: .5em; width: 70%"><a
                                    href="<c:url value="/group?id=${group.groupID}"/> ">
                                <c:out value="${group.groupName}"/></a>
                            </h5>
                        </div>
                    </div>
                </c:forEach>

                <nav aria-label="...">
                    <ul class="pagination">
                        <c:if test="${requestScope.currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="<c:url value="/search?currentPage=${requestScope.currentPage-1}&value=${requestScope.value}&tab=groups"/>">Previous</a>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${requestScope.groupsNumOfPages}" var="i">
                            <c:choose>
                                <c:when test="${requestScope.currentPage eq i}">
                                    <li class="page-item active">
                                        <a class="page-link" href="#">${i} <span
                                                class="sr-only">(current)</span></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="<c:url value="/search?currentPage=${i}&value=${requestScope.value}&tab=groups"/>">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${requestScope.currentPage lt requestScope.groupsNumOfPages}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="<c:url value="/search?currentPage=${requestScope.currentPage+1}&value=${requestScope.value}&tab=groups"/>">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>

            </div>
        </div>
    </div>
</section>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>