<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <style>
        form {
            margin-bottom: 0
        }
    </style>

    <title>Friends</title>
</head>
<body>
<section>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="card groups">
                    <div class="card-header">
                        <h4 class="card-title">Friends</h4>
                    </div>
                    <div class="card-body">
                        <c:forEach var="friend" items="${requestScope.friends}">
                            <div class="group-item">
                                <div class="d-flex">
                                    <h5 style="margin-bottom: .7em; margin-right: .5em; width: 70%"><a
                                            href="<c:url value="/account?id=${friend.accountID}"/> ">
                                        <c:out value="${friend.name} ${friend.surname}"/></a>
                                    </h5>
                                    <c:if test="${requestScope.isAbleToModify}">
                                        <c:forEach var="request" items="${requestScope.acceptedRequests}">
                                            <c:if test="${request.creatorID == friend.accountID || request.recipientID == friend.accountID}">
                                                <c:set var="creatorId" scope="page" value="${request.creatorID}"/>
                                                <c:set var="recipientId" scope="page" value="${request.recipientID}"/>
                                            </c:if>
                                        </c:forEach>

                                        <form action="<%= request.getContextPath()%>/request" method="post">
                                            <input type="hidden" name="creatorId" value="${creatorId}">
                                            <input type="hidden" name="accountId" value="${recipientId}">
                                            <input type="hidden" name="reqType" value="user">
                                            <input type="hidden" name="reqStatus" value="0">
                                            <input type="hidden" name="operation" value="unfriend">

                                            <button type="submit" class="btn btn-primary" name="Save" value="Save">
                                                Unfriend
                                            </button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <c:if test="${requestScope.isAbleToModify}">
                <div class="col-md-4">
                    <div class="card groups">
                        <div class="card-header">
                            <h4 class="card-title">Pending Requests</h4>
                        </div>

                        <div class="card-body">
                            <c:forEach var="pending" items="${requestScope.pendingAccounts}">
                                <div class="group-item">
                                    <div class="d-flex">
                                        <h5 style="margin-bottom: .7em; margin-right: .5em; width: 70%"><a
                                                href="<c:url value="/account?id=${pending.accountID}"/> ">
                                            <c:out value="${pending.name} ${pending.surname}"/></a>
                                        </h5>


                                        <form action="<%= request.getContextPath()%>/request" method="post">
                                            <input type="hidden" name="creatorId" value="${pending.accountID}">
                                            <input type="hidden" name="accountId" value="${requestScope.visitorID}">
                                            <input type="hidden" name="reqType" value="user">
                                            <input type="hidden" name="reqStatus" value="1">
                                            <input type="hidden" name="operation" value="accept">

                                            <button type="submit" class="btn btn-primary" name="Save" value="Save">
                                                Add Friend
                                            </button>
                                        </form>

                                        <form action="<%= request.getContextPath()%>/request" method="post">
                                            <input type="hidden" name="creatorId" value="${pending.accountID}">
                                            <input type="hidden" name="accountId" value="${requestScope.visitorID}">
                                            <input type="hidden" name="reqType" value="user">
                                            <input type="hidden" name="reqStatus" value="2">
                                            <input type="hidden" name="operation" value="decline">

                                            <button type="submit" class="btn btn-outline-warning" name="Save"
                                                    value="Save">
                                                Decline
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="col-md-4">
                <div class="card groups">
                    <div class="card-header">
                        <h4 class="card-title">Outgoing Requests</h4>
                    </div>
                    <div class="card-body">
                        <c:forEach var="outgoing" items="${requestScope.outgoingAccounts}">
                            <div class="group-item">
                                <div class="d-flex">
                                    <h5 style="margin-bottom: .7em; margin-right: .5em; width: 70%"><a
                                            href="<c:url value="/account?id=${outgoing.accountID}"/> ">
                                        <c:out value="${outgoing.name} ${outgoing.surname}"/></a>
                                    </h5>

                                    <c:if test="${requestScope.isAbleToModify}">
                                        <form action="<%= request.getContextPath()%>/request" method="post">
                                            <input type="hidden" name="creatorId" value="${requestScope.visitorID}">
                                            <input type="hidden" name="accountId" value="${outgoing.accountID}">
                                            <input type="hidden" name="reqType" value="user">
                                            <input type="hidden" name="reqStatus" value="2">
                                            <input type="hidden" name="operation" value="decline">

                                            <button type="submit" class="btn btn-primary" name="Save"
                                                    value="Save">
                                                Unfollow
                                            </button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
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
