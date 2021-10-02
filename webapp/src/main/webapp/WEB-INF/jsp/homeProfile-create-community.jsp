<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Create Group"/>
</jsp:include>

<%@include file="templates/navbar.jsp" %>

<div class="container mb-3" id="content">
    <div class="row">

        <jsp:include page="templates/profile-sidebar.jsp">
            <jsp:param name="userId" value="${sessionScope.homeAccountId}"/>
            <jsp:param name="picAttached" value="${sessionScope.homeAccount.picAttached}"/>
        </jsp:include>

        <div class="col-9">
            <main>
                <div class="card">
                    <div class="card-body p-4">
                        <div class="row justify-content-center">
                            <div class="col-6">
                                <h3>Create a new community</h3>

                                <form method="POST" action="${pageContext.request.contextPath}/community/new"
                                      enctype="multipart/form-data">
                                    <div class="input-community input-community-sm mb-3">
                                        <div class="input-community-prepend">
                                            <span class="input-community-text">Community name</span>
                                        </div>
                                        <input type="text" class="form-control" name="communityName">
                                    </div>

                                    <div class="input-community">
                                        <div class="input-community-prepend">
                                            <span class="input-community-text">About</span>
                                        </div>
                                        <textarea class="form-control" name="commDescription"></textarea>
                                    </div>

                                    <br>
                                    <div class="form-community">
                                        <label for="exampleFormControlFile1">Upload account picture</label>
                                        <input type="file" class="form-control-file" id="exampleFormControlFile1"
                                               name="picture">
                                    </div>

                                    <br>
                                    <button type="submit" class="btn btn-info" name="Save" value="Save">
                                        Create
                                    </button>
                                    <button type="button" class="btn btn-warning">
                                        <a href="<c:url value="/account/${sessionScope.homeAccountId}"/>">
                                            Cancel
                                        </a>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp" %>