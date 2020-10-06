<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Account Page</title>
</head>
<body>
<section>
    <div class="container">
        <div class="row">
            <div class="col-md-8">
                <div class="profile" style="margin-bottom: 10px">
                    <h1 class="page-header"><c:out
                            value="${requestScope.account.surname} ${requestScope.account.name}"/></h1>
                    <div class="row">
                        <div class="col-md-4">
                            <img src="${pageContext.request.contextPath}/displayPicture?id=${requestScope.id}"
                                 class="img-thumbnail" alt="">
                        </div>
                        <div class="col-md-8">
                            <ul>
                                <li><strong>Full name </strong><c:out
                                        value="${requestScope.account.surname} ${requestScope.account.name} ${requestScope.account.middlename} "/>
                                </li>
                                <li><strong>Email </strong><c:out value="${requestScope.account.email}"/></li>
                                <li><strong>Birthday </strong><c:out value="${requestScope.account.dateOfBirth}"/></li>
                                <li><strong>Home Phone </strong><c:out value="${requestScope.account.homePhone}"/></li>
                                <li><strong>Work Phone </strong><c:out value="${requestScope.account.workPhone}"/></li>
                                <li><strong>Skype </strong><c:out value="${requestScope.account.skype}"/></li>
                                <li><strong>Icq </strong><c:out value="${requestScope.account.icq}"/></li>
                                <li><strong>Home Address </strong><c:out value="${requestScope.account.homeAddress}"/>
                                </li>
                                <li><strong>Work Address </strong><c:out value="${requestScope.account.workAddress}"/>
                                </li>
                                <li><strong>About </strong><c:out value="${requestScope.account.addInfo}"/></li>
                            </ul>
                        </div>
                    </div>
                    <br><br>
                    <button type="button" class="btn btn-outline-warning">
                        <a href="<c:url value="/logOut"/>" style="color: lightcoral">
                            LogOut
                        </a>
                        <button type="button" class="btn btn-outline-warning">
                            <a href="<c:url value="/update?id=${sessionScope.account.accountID}"/>" style="color: navy">
                                Update
                            </a>
                        </button>
                        <button type="button" class="btn btn-outline-warning">
                            <a href="<c:url value="/createGroup"/>" style="color: tomato">
                                Create group
                            </a>
                        </button>

                        <c:if test="${sessionScope.account.accountID != requestScope.account.accountID}">
                        <form action="<%= request.getContextPath()%>/request" method="post">
                            <input type="hidden" name="creatorId" value="${sessionScope.account.accountID}">
                            <input type="hidden" name="accountId" value="${requestScope.account.accountID}">
                            <input type="hidden" name="reqType" value="user">
                            <input type="hidden" name="reqStatus" value="0">
                            <input type="hidden" name="operation" value="create">

                            <button type="submit" class="btn btn-primary" name="Save" value="Save">
                                Follow
                            </button>
                        </form>
                        </c:if>
                </div>

                <div class="row">
                    <div class="col-md-4">
                        <div class="card groups">
                            <div class="card-header">
                                <h4 class="card-title">Groups</h4>
                            </div>
                            <div class="card-body">
                                <c:forEach var="group" items="${requestScope.groupsList}">
                                    <div class="group-item">
                                        <h5><a href="<c:url value="/group?id=${group.groupID}"/>">
                                            <c:out value="${group.groupName}"/></a></h5>
                                        <p></p>
                                    </div>
                                </c:forEach>

                                <a href="#" class="btn btn-primary">View All Groups</a>
                            </div>
                        </div>
                    </div>
                </div>

                <button type="button" class="btn btn-outline-warning">
                    <a href="<c:url value="/friends?id=${requestScope.id}"/>" style="color: darkgoldenrod">
                        Friends
                    </a>
                </button>
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
