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
            <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/styles.css"/>"/>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>

    <title>Update</title>
</head>
<body>
<div class="container">
    <div class="row justify-content-center align-items-center vh-100">
        <div class="col-4">
            <h3>Update account</h3>

            <form id="form" name="form"
                  action="${pageContext.request.contextPath}/update?id=${requestScope.account.accountID}"
                  method="post"
                  enctype="multipart/form-data">

                <input type="hidden" name="id" value="${requestScope.account.accountID}">

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Surname</span>
                    </div>
                    <input type="text" id="surname" class="form-control" name="surname"
                           value="${requestScope.account.surname}">
                    <small class="form-text text-danger input-error"></small>
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Name</span>
                    </div>
                    <input type="text" id="name" class="form-control" name="name" value="${requestScope.account.name}">
                    <small class="form-text text-danger input-error"></small>
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Middlename</span>
                    </div>
                    <input type="text" id="middlename" class="form-control" name="middlename"
                           value="${requestScope.account.middlename}">
                    <small class="form-text text-danger input-error"></small>
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Password</span>
                    </div>
                    <input type="text" id="password" class="form-control" name="password"
                           value="${requestScope.account.password}">
                    <small class="form-text text-danger input-error"></small>
                </div>

                <div class="form-group-sm">
                    <div class="phoneList" id="list">
                        <c:forEach var="phone" items="${requestScope.account.phones}">
                            <div class="input-group input-group mb-3">

                                    <div class="input-group-prepend">
                                        <span class="input-group-text"><c:out
                                                value="${phone.phoneType == 'work' ? 'Work' : 'Home'}"/> Phone</span>
                                    </div>
                                    <input type="text" id="phone" class="form-control"
                                           name="${phone.phoneType == 'work' ? 'workPhone[]' : 'homePhone[]'}"
                                           value="${phone.phoneNumber}">
                                    <button type="button" class="btn btn-danger btn-sm delete-button">Delete
                                    </button>

                            </div>
                        </c:forEach>
                    </div>

                    <div class="d-flex mb-3">
                        <select class="select" id="phoneType">
                            <option value="home">Home</option>
                            <option value="work">Work</option>
                        </select>
                        <input type="text" id="phoneInput" class="form-control"
                               aria-label="Text input with dropdown button"
                               value="">
                        <button type="button" id="addButton" class="btn btn-primary btn-sm" name="Save">Add</button>
                    </div>
                </div>

                <div class="input-group input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Birthday</span>
                    </div>
                    <input type="date" id="dateOfBirth" class="form-control" name="dateOfBirth"
                           value="${requestScope.account.dateOfBirth}">
                    <small class="form-text text-danger input-error"></small>
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Skype</span>
                    </div>
                    <input type="text" id="skype" class="form-control" name="skype"
                           value="${requestScope.account.skype}">
                    <small class="form-text text-danger input-error"></small>
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Icq</span>
                    </div>
                    <input type="text" id="icq" class="form-control" name="icq" value="${requestScope.account.icq}">
                    <small class="form-text text-danger input-error"></small>
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Home Address</span>
                    </div>
                    <input type="text" id="homeAddress" class="form-control" name="homeAddress"
                           value="${requestScope.account.homeAddress}">
                    <small class="form-text text-danger input-error"></small>
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Work Address</span>
                    </div>
                    <input type="text" id="workAddress" class="form-control" name="workAddress"
                           value="${requestScope.account.workAddress}">
                    <small class="form-text text-danger input-error"></small>
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">additional information</span>
                    </div>
                    <input type="text" id="addInfo" class="form-control" name="addInfo"
                           value="${requestScope.account.addInfo}">
                </div>

                <br>
                <div class="form-group">
                    <label for="exampleFormControlFile1">Upload account picture</label>
                    <input type="file" class="form-control-file" id="exampleFormControlFile1" name="picture"
                           value="${requestScope.account.picture}">
                </div>

                <br>
                <button type="submit" id="submitButton" class="btn btn-primary" name="Save" value="Save">
                    Update
                </button>
                <button type="button" class="btn btn-outline-warning">
                    <a href="<c:url value="/account?id=${requestScope.account.accountID}"/>" style="color: navy">
                        Cancel
                    </a>
                </button>
            </form>
        </div>
    </div>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/dynamicPhoneInput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/validateForm.js"></script>
</body>
</html>
