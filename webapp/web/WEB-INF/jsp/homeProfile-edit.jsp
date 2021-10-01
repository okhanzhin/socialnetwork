<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Edit"/>
</jsp:include>

<%@include file="templates/navbar.jsp" %>

<div class="container mb-3" id="content">
    <div class="row">

        <jsp:include page="templates/profile-sidebar.jsp">
            <jsp:param name="pageTitle" value="Edit"/>
            <jsp:param name="userId" value="${sessionScope.homeAccountId}"/>
            <jsp:param name="picAttached" value="${sessionScope.homeAccount.picAttached}"/>
        </jsp:include>

        <div class="col-9">
            <main>
                <div class="card">
                    <div class="card-body p-4">
                        <div class="row justify-content-center">
                            <div class="col-6">
                                <ul class="nav nav-tabs" id="myTab" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active" id="update-tab" data-toggle="tab" href="#update"
                                           role="tab" aria-controls="update" aria-selected="true">Update</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" id="xml-tab" data-toggle="tab" href="#xml"
                                           role="tab" aria-controls="xml" aria-selected="false">Xml</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" id="change-password-tab" data-toggle="tab" href="#change-password"
                                           role="tab" aria-controls="change-password" aria-selected="false">Change Password</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="myTabContent">
                                    <div class="tab-pane fade pt-3 show active" id="update" role="tabpanel"
                                         aria-labelledby="home-tab">
                                        <h3>Update account</h3>

                                        <c:if test="${requestScope.logXmlFileError.length() != 0}">
                                            <div class="alert alert-danger">
                                                <c:out value="${requestScope.logXmlFileError}"/>
                                            </div>
                                        </c:if>

                                        <c:if test="${requestScope.successNotification.length() != 0}">
                                            <div class="alert alert-success">
                                                <c:out value="${requestScope.successNotification}"/>
                                            </div>
                                        </c:if>

                                        <form id="form" method="POST"
                                              action="${pageContext.request.contextPath}/account/${accountTransfer.accountID}/update"
                                              enctype="multipart/form-data">
                                            <input type="hidden" name="accountID" value="${accountTransfer.accountID}">
                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Surname</span>
                                                </div>
                                                <input type="text" id="surname" class="form-control" name="surname"
                                                       value="${accountTransfer.surname}">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>
                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Name</span>
                                                </div>
                                                <input type="text" id="name" class="form-control" name="name"
                                                       value="${accountTransfer.name}">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>
                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Middlename</span>
                                                </div>
                                                <input type="text" id="middlename" class="form-control"
                                                       name="middlename"
                                                       value="${accountTransfer.middlename}">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>
                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Email</span>
                                                </div>
                                                <input type="text" id="email" class="form-control"
                                                       name="email" value="${accountTransfer.email}">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>

                                            <input type="hidden" id="password" class="form-control" name="password"
                                                   value="${accountTransfer.password}">
                                            <div class="form-community-sm mb-3">
                                                <div class="phoneList" id="list">
                                                    <c:forEach var="phone" items="${accountTransfer.phones}">
                                                        <div class="input-community mb-3">
                                                            <div class="d-flex w-100">
                                                                <div class="input-column">
                                                                    <div class="input-community-prepend">
                                                            <span class="input-community-text">
                                                                <c:out value="${phone.phoneType == 'work' ? 'Work ' : 'Home '}"/>
                                                                Phone
                                                            </span>
                                                                    </div>
                                                                </div>
                                                                <div class="input-column">
                                                                    <input type="text" id="${phone.phoneID}" class="form-control"
                                                                           name="${phone.phoneType == 'work' ? 'workPhone' : 'homePhone'}"
                                                                           value="${phone.phoneNumber}">

                                                                    <c:if test="${phone != accountTransfer.phones[0]}">
                                                                        <button type="button" class="btn-edit-phone delete-button">
                                                                            Delete
                                                                        </button>
                                                                    </c:if>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <div class="d-flex mb-1">
                                                    <select class="select" id="phoneType">
                                                        <option value="home">Home Phone</option>
                                                        <option value="work">Work Phone</option>
                                                    </select>
                                                    <input type="text" id="phoneInput" class="form-control"
                                                           aria-label="Text input with dropdown button" value="">
                                                    <button type="button" id="addButton" class="btn-edit-phone"
                                                            name="Save">Add
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="input-community input-community mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Birthday</span>
                                                </div>
                                                <input type="date" id="dateOfBirth" class="form-control"
                                                       name="dateOfBirth"
                                                       value="${accountTransfer.dateOfBirth}">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>
                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Skype</span>
                                                </div>
                                                <input type="text" id="skype" class="form-control" name="skype"
                                                       value="${accountTransfer.skype}">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>
                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Icq</span>
                                                </div>
                                                <input type="text" id="icq" class="form-control" name="icq"
                                                       value="${accountTransfer.icq}">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>
                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Home Address</span>
                                                </div>
                                                <input type="text" id="homeAddress" class="form-control"
                                                       name="homeAddress"
                                                       value="${accountTransfer.homeAddress}">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>
                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Work Address</span>
                                                </div>
                                                <input type="text" id="workAddress" class="form-control"
                                                       name="workAddress"
                                                       value="${accountTransfer.workAddress}">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>
                                            <div class="input-community mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">additional information</span>
                                                </div>
                                                <input type="text" id="addInfo" class="form-control" name="addInfo"
                                                       value="${accountTransfer.addInfo}">
                                            </div>
                                            <br>

                                            <input type="hidden" id="role" class="form-control" name="role"
                                                   value="${accountTransfer.role}">

                                            <input type="hidden" id="enabled" class="form-control" name="enabled"
                                                   value="${accountTransfer.enabled}">

                                            <div class="form-community-sm mb-3">
                                                <div class="input-community mb-3">
                                                    <div class="d-flex w-100 align-items-end justify-content-between">
                                                        <div class="form-community">
                                                            <label for="picture">Upload account picture</label>
                                                            <input type="file" class="form-control-file" id="picture"
                                                                   name="picture"
                                                                   value="${accountTransfer.picture}">
                                                        </div>
                                                        <c:if test="${accountTransfer.picAttached}">
                                                        <div class="d-flex">
                                                            <a class="btn btn-warning mr-1 text-nowrap"
                                                               href="<c:url value="/account/${accountTransfer.accountID}/deletePicture"/>">
                                                                Delete Picture
                                                            </a>
                                                        </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                            <br>

                                            <div class="d-flex">
                                                <button type="submit" id="submitButton" class="btn btn-info"
                                                        name="Save"
                                                        value="Save">
                                                    Update
                                                </button>
                                                <a class="btn btn-warning"
                                                   href="<c:url value="/account/${accountTransfer.accountID}"/>">
                                                    Cancel
                                                </a>
                                            </div>
                                        </form>
                                    </div>

                                    <div class="tab-pane fade pt-3" id="xml" role="tabpanel"
                                         aria-labelledby="profile-tab">
                                        <h3>Upload/Download Xml-account</h3>

                                        <div class="form-community-sm mb-3">
                                            <div class="input-community mb-3">
                                                <div class="d-flex w-100">
                                                    <div class="form-community w-100">
                                                        <form id="xml-form" method="POST"
                                                              action="${pageContext.request.contextPath}/account/${accountTransfer.accountID}/uploadXml"
                                                              enctype="multipart/form-data">
                                                            <div class="form-community-sm mb-3">
                                                                <div class="input-community mb-3">
                                                                    <div class="d-flex w-100">
                                                                        <div class="form-community">
                                                                            <input type="file" id="xmlFile"
                                                                                   class="form-control-file"
                                                                                   name="xmlFile">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="d-flex w-100 justify-content-between">
                                                                <button type="submit" class="btn btn-info" name="Save"
                                                                        value="Save">
                                                                    Upload
                                                                </button>
                                                                <a class="btn btn-info"
                                                                   href="<c:url value="/account/${accountTransfer.accountID}/downloadXml"/>">
                                                                    Download
                                                                </a>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="tab-pane fade pt-3" id="change-password" role="tabpanel"
                                         aria-labelledby="password-tab">
                                        <h3>Change Password</h3>

                                        <form id="password-form" method="POST"
                                              action="${pageContext.request.contextPath}/account/${accountTransfer.accountID}/changePassword"
                                              enctype="multipart/form-data">

                                            <input type="hidden" name="accountID" value="${accountTransfer.accountID}">

                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Current password</span>
                                                </div>
                                                <input type="text" id="stored-password" class="form-control"
                                                       name="current-password">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>

                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">New password</span>
                                                </div>
                                                <input type="text" id="new-password" class="form-control"
                                                       name="new-password">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>

                                            <div class="input-community input-community-sm mb-3">
                                                <div class="input-community-prepend">
                                                    <span class="input-community-text">Repeat new password</span>
                                                </div>
                                                <input type="text" id="confirm-password" class="form-control"
                                                       name="confirm-password">
                                                <small class="form-text text-danger input-error"></small>
                                            </div>

                                            <div class="d-flex">
                                                <button type="submit" id="submitButton2" class="btn btn-info"
                                                        name="Save"
                                                        value="Save">
                                                    Change
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/utils/verificationUtils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dynamicPhoneInput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/verification/updateForm.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/verification/changePasswordForm.js"></script>
