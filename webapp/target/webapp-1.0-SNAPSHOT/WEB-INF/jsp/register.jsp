<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Registration"/>
</jsp:include>

<%@include file="templates/navbar.jsp" %>

<div class="container mt-auto">
    <div class="row justify-content-center align-items-center">
        <div class="col-5">
            <div class="text-center">
                <h3>Create a new account</h3>
            </div>

            <div class="card p-4">
                <form id="form" method="POST" action="${pageContext.request.contextPath}/account/new"
                      enctype="multipart/form-data">

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">*Surname</span>
                        </div>
                        <input type="text" id="surname" class="form-control" name="surname"/>
                        <small class="form-text text-danger input-error"></small>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">*Name</span>
                        </div>
                        <input type="text" id="name" class="form-control" name="name"/>
                        <small class="form-text text-danger input-error"></small>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">Middlename</span>
                        </div>
                        <input type="text" id="middlename" class="form-control" name="middlename"/>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">*Email</span>
                        </div>
                        <input type="text" id="email" class="form-control" name="email"/>
                        <small class="form-text text-danger input-error"></small>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">*Password</span>
                        </div>
                        <input type="text" id="password" class="form-control" name="password"/>
                        <small class="form-text text-danger input-error"></small>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">Date of Birth</span>
                        </div>
                        <input type="date" id="dateOfBirth" class="form-control" name="dateOfBirth"/>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">Home Phone</span>
                        </div>
                        <input type="text" id="homePhone" class="form-control" name="homePhone"
                               pattern="(\+7[0-9]{10}|8[0-9]{10})"
                               title="'8-Ten-Digits' or '+7-Ten-Digits'">
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">Work Phone</span>
                        </div>
                        <input type="text" id="workPhone" class="form-control" name="workPhone"
                               pattern="(\+7[0-9]{10}|8[0-9]{10})"
                               title="'8-Ten-Digits' or '+7-Ten-Digits'">
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">Skype</span>
                        </div>
                        <input type="text" id="skype" class="form-control" name="skype"/>
                        <small class="form-text text-danger input-error"></small>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">Icq</span>
                        </div>
                        <input type="text" id="icq" class="form-control" name="icq"/>
                        <small class="form-text text-danger input-error"></small>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">Home Address</span>
                        </div>
                        <input type="text" id="homeAddress" class="form-control" name="homeAddress"/>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">Work Address</span>
                        </div>
                        <input type="text" id="workAddress" class="form-control" name="workAddress"/>
                    </div>

                    <div class="input-community input-community-sm mb-3">
                        <div class="input-community-prepend">
                            <span class="input-community-text">About</span>
                        </div>
                        <textarea class="form-control" placeholder=" Tell me a story..." name="addInfo"></textarea>
                    </div>

                    <input type="hidden" class="form-control" name="role" value="USER"/>
                    <input type="hidden" class="form-control" name="enabled" value="true"/>

                    <br>
                    <div class="form-community">
                        <label for="inputGroupFile">Upload account picture</label>
                        <input type="file" class="form-control-file" id="inputGroupFile" name="picture"/>
                    </div>

                    <br>
                    <button type="submit" class="btn btn-info" name="Save" value="Save">
                        Create
                    </button>
                    <a class="btn btn-warning" href="<c:url value="/login"/>">
                        Cancel
                    </a>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/utils/verificationUtils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/verification/registrationForm.js"></script>