<%@ page import="com.getjavajob.training.okhanzhin.socialnetwork.service.GroupService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="templates/navbar.jsp"%>

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

    <title>Group</title>
</head>
<body>
<section>
    <div class="container">
        <div class="row">
            <div class="col-md-8">
                <div class="profile" style="margin-bottom: 10px">
                    <h1 class="page-header"><c:out value="${requestScope.group.groupName}"/></h1>
                    <div class="row">
                        <div class="col-md-4">
                            <img src="${pageContext.request.contextPath}/displayPicture?groupId=${requestScope.group.groupID}"
                                 class="img-thumbnail" alt="">
                        </div>
                        <p></p>
                    </div>

                    <div class="d-flex">
                        <c:if test="${requestScope.isAbleToModify}">
                            <a class="btn btn-outline-warning"
                               href="</>"
                               style="color: navy">
                                Update
                            </a>
                        </c:if>

                        <c:if test="${requestScope.isMember == 'false'}">
                            <form action="<%= request.getContextPath()%>/request" method="post">
                                <input type="hidden" name="creatorId" value="${requestScope.visitorID}">
                                <input type="hidden" name="groupId" value="${requestScope.group.groupID}">
                                <input type="hidden" name="reqType" value="group">
                                <input type="hidden" name="reqStatus" value="0">
                                <input type="hidden" name="operation" value="create">

                                <button type="submit" class="btn btn-primary" name="Save" value="Save">
                                    Join Group
                                </button>
                            </form>
                        </c:if>

                        <c:if test="${requestScope.visitorStatus != null}">
                            <c:if test="${requestScope.visitorStatus > 0}">
                                <form action="<%= request.getContextPath()%>/processMember" method="post">
                                    <input type="hidden" name="groupId" value="${requestScope.group.groupID}">
                                    <input type="hidden" name="accountId" value="${requestScope.visitorID}">
                                    <input type="hidden" name="operation" value="delete">

                                    <button type="submit" class="btn btn-primary" name="Save" value="Save">
                                        Leave Group
                                    </button>
                                </form>
                            </c:if>
                        </c:if>
                        <p></p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-5">
                <div class="card groups">
                    <div class="card-header">
                        <h4 class="card-title">Members</h4>
                    </div>
                    <div class="card-body">
                        <c:forEach var="account" items="${requestScope.accountsList}">
                            <div class="group-item">
                                <div class="d-flex">
                                    <h5 style="margin-bottom: .7em; margin-right: .5em; width: 70%"><a
                                            href="<c:url value="/account?id=${account.accountID}"/> ">
                                        <c:out value="${account.name} ${account.surname}"/></a>
                                    </h5>

                                    <c:if test="${requestScope.isAbleToModify && account.accountID != requestScope.visitorID}">
                                        <c:set var="isModerator" scope="page" value="false"/>
                                        <c:if test="${requestScope.moderatorsList.size() != 0}">
                                            <c:forEach var="item" items="${requestScope.moderatorsList}">
                                                <c:if test="${item.accountID == account.accountID}">
                                                    <c:set var="isModerator" scope="page" value="true"/>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>

                                        <c:if test="${account.accountID != requestScope.ownerID}">
                                            <c:if test="${!isModerator}">
                                                <form action="<%= request.getContextPath()%>/processMember"
                                                      method="post">
                                                    <input type="hidden" name="groupId"
                                                           value="${requestScope.group.groupID}">
                                                    <input type="hidden" name="accountId" value="${account.accountID}">
                                                    <input type="hidden" name="operation" value="makeModerator">

                                                    <button style="white-space: nowrap" type="submit"
                                                            class="btn btn-primary"
                                                            name="Save" value="Save">
                                                        Make Admin
                                                    </button>
                                                </form>
                                            </c:if>


                                            <c:if test="${isModerator}">
                                                <form action="<%= request.getContextPath()%>/processMember"
                                                      method="post">
                                                    <input type="hidden" name="groupId"
                                                           value="${requestScope.group.groupID}">
                                                    <input type="hidden" name="accountId" value="${account.accountID}">
                                                    <input type="hidden" name="operation" value="makeUser">

                                                    <button style="white-space: nowrap" type="submit"
                                                            class="btn btn-primary"
                                                            name="Save" value="Save">
                                                        Make User
                                                    </button>
                                                </form>
                                            </c:if>

                                            <form action="<%= request.getContextPath()%>/processMember" method="post">
                                                <input type="hidden" name="groupId"
                                                       value="${requestScope.group.groupID}">
                                                <input type="hidden" name="accountId" value="${account.accountID}">
                                                <input type="hidden" name="operation" value="delete">

                                                <button style="white-space: nowrap" type="submit"
                                                        class="btn btn-outline-warning"
                                                        name="Save" value="Save">
                                                    Delete
                                                </button>
                                            </form>
                                        </c:if>
                                    </c:if>
                                    <p></p>
                                </div>
                            </div>
                        </c:forEach>
                        <a href="#" class="btn btn-primary">View All Members</a>
                    </div>
                </div>
            </div>

            <c:if test="${requestScope.isAbleToModify}">
            <div class="col-md-4">
                <div class="card groups">
                    <div class="card-header">
                        <h4 class="card-title">Requests</h4>
                    </div>
                    <div class="card-body">
                        <c:forEach var="unconfirmedAccount" items="${requestScope.unconfirmedList}">
                            <div class="group-item">
                                <div class="d-flex">
                                    <h5 style="margin-bottom: .7em; margin-right: .5em; width: 100%"><a
                                            href="<c:url value="/account?id=${unconfirmedAccount.accountID}"/> ">
                                        <c:out value="${unconfirmedAccount.name} ${unconfirmedAccount.surname}"/></a>
                                    </h5>

                                    <form action="<%= request.getContextPath()%>/request" method="post">
                                        <input type="hidden" name="creatorId"
                                               value="${unconfirmedAccount.accountID}">
                                        <input type="hidden" name="groupId" value="${requestScope.group.groupID}">
                                        <input type="hidden" name="reqType" value="group">
                                        <input type="hidden" name="reqStatus" value="1">
                                        <input type="hidden" name="operation" value="accept">

                                        <button type="submit" class="btn btn-outline-warning" name="Save"
                                                value="Save">
                                            Accept
                                        </button>
                                    </form>

                                    <form action="<%= request.getContextPath()%>/request" method="post">
                                        <input type="hidden" name="creatorId"
                                               value="${unconfirmedAccount.accountID}">
                                        <input type="hidden" name="groupId" value="${requestScope.group.groupID}">
                                        <input type="hidden" name="reqType" value="group">
                                        <input type="hidden" name="reqStatus" value="2">
                                        <input type="hidden" name="operation" value="decline">

                                        <button type="submit" class="btn btn-outline-warning" name="Save"
                                                value="Save">
                                            Decline
                                        </button>
                                    </form>
                                </div>
                                <p></p>
                            </div>
                        </c:forEach>
                        <a href="#" class="btn btn-primary">View All Requests</a>
                        </c:if>
                    </div>
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