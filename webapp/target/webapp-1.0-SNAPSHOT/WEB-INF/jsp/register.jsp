<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Registration</title>
</head>
<body>
<div class="container">
    <div class="row justify-content-center align-items-center vh-100">
        <div class="col-4">
            <h3>Create a new account</h3>

            <form action="<%= request.getContextPath() %>/register" method="post" enctype="multipart/form-data">

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Surname</span>
                    </div>
                    <input type="text" class="form-control" name="surname">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Name</span>
                    </div>
                    <input type="text" class="form-control" name="name">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Middlename</span>
                    </div>
                    <input type="text" class="form-control" name="middlename">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Email</span>
                    </div>
                    <input type="text" class="form-control" name="email">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Password</span>
                    </div>
                    <input type="text" class="form-control" name="password">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Date of Birth</span>
                    </div>
                    <input class="form-control" type="date" value="" name="dateOfBirth">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Home Phone</span>
                    </div>
                    <input type="text" class="form-control" name="homePhone">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Work Phone</span>
                    </div>
                    <input type="text" class="form-control" name="workPhone">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Skype</span>
                    </div>
                    <input type="text" class="form-control" name="skype">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Icq</span>
                    </div>
                    <input type="text" class="form-control" name="icq">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Home Address</span>
                    </div>
                    <input type="text" class="form-control" name="homeAddress">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Work Address</span>
                    </div>
                    <input type="text" class="form-control" name="workAddress">
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">About</span>
                    </div>
                    <textarea class="form-control" name="addInfo"></textarea>
                </div>

                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Network Role</span>
                    </div>
                    <input type="text" class="form-control" name="role">
                </div>

                <br>
                <div class="form-group">
                    <label for="exampleFormControlFile1">Upload account picture</label>
                    <input type="file" class="form-control-file" id="exampleFormControlFile1" name="picture">
                </div>

                <br>
                <button type="submit" class="btn btn-primary" name="Save" value="Save">
                    Create
                </button>
                <a class="btn btn-outline-warning" href="<c:url value="/logIn"/>" style="color: navy">
                    Cancel
                </a>
            </form>
        </div>
    </div>
</div>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
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
