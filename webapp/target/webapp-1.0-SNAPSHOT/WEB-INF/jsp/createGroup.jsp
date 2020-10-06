<%--
  Created by IntelliJ IDEA.
  User: okha
  Date: 20.08.20
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
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

    <title>Create Group</title>
</head>
<body>
<div class="container">
    <div class="row justify-content-center align-items-center vh-100">
        <div class="col-4">
            <h3>Create a new group</h3>

            <form action="<%= request.getContextPath() %>/createGroup" method="post" enctype="multipart/form-data">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Group name</span>
                    </div>
                    <input type="text" class="form-control" name="groupName">
                </div>

                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text">About</span>
                    </div>
                    <textarea class="form-control" name="description"></textarea>
                </div>

                <br>
                <div class="form-group" >
                    <label for="exampleFormControlFile1">Upload account picture</label>
                    <input type="file" class="form-control-file" id="exampleFormControlFile1" name="picture">
                </div>

                <br>
                <button type="submit" class="btn btn-primary" name="Save" value="Save">
                    Create
                </button>
                <button type="button" class="btn btn-outline-warning">
                    <a href="<c:url value="/account?id=${sessionScope.id}"/>" style="color: navy">
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
