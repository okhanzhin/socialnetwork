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

    <title>Update</title>
</head>
<body>
<div class="container">
    <div class="row justify-content-center align-items-center vh-100">
        <div class="col-4">
            <h3>Update account</h3>

            <form action="${pageContext.request.contextPath}/update?id=${requestScope.account.accountID}" method="post"
                  enctype="multipart/form-data">

                <input type="hidden" name="id" value="${requestScope.account.accountID}">

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Surname</span>
                    </div>
                    <input type="text" class="form-control" name="surname" value="${requestScope.account.surname}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Name</span>
                    </div>
                    <input type="text" class="form-control" name="name" value="${requestScope.account.name}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Middlename</span>
                    </div>
                    <input type="text" class="form-control" name="middlename"
                           value="${requestScope.account.middlename}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Password</span>
                    </div>
                    <input type="text" class="form-control" name="password" value="${requestScope.account.password}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Birthday</span>
                    </div>
                    <input class="form-control" type="date" name="dateOfBirth"
                           value="${requestScope.account.dateOfBirth}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Home Phone</span>
                    </div>
                    <input type="text" class="form-control" name="homePhone" value="${requestScope.account.homePhone}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Work Phone</span>
                    </div>
                    <input type="text" class="form-control" name="workPhone" value="${requestScope.account.workPhone}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Skype</span>
                    </div>
                    <input type="text" class="form-control" name="skype" value="${requestScope.account.skype}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Icq</span>
                    </div>
                    <input type="text" class="form-control" name="icq" value="${requestScope.account.icq}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Home Address</span>
                    </div>
                    <input type="text" class="form-control" name="homeAddress"
                           value="${requestScope.account.homeAddress}">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Work Address</span>
                    </div>
                    <input type="text" class="form-control" name="workAddress"
                           value="${requestScope.account.workAddress}">
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">additional information</span>
                    </div>
                    <input type="text" class="form-control" name="addInfo" value="${requestScope.account.addInfo}">
                </div>

                <br>
                <div class="form-group">
                    <label for="exampleFormControlFile1">Upload account picture</label>
                    <input type="file" class="form-control-file" id="exampleFormControlFile1" name="picture"
                           value="${requestScope.account.picture}">
                </div>

                <br>
                <button type="submit" class="btn btn-primary" name="Save" value="Save">
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

<script src=">https:</input>//code.jquery.com/jquery-3.5.1.slim.min.js"
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
