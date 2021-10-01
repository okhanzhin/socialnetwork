<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Edit"/>
</jsp:include>

<%@include file="templates/navbar.jsp" %>

<div class="container mb-3" id="content">
    <div class="row">

        <jsp:include page="templates/community-sidebar.jsp">
            <jsp:param name="communityId" value="${sessionScope.activeCommunity.commID}"/>
            <jsp:param name="picAttached" value="${sessionScope.activeCommunity.picAttached}"/>
        </jsp:include>

        <div class="col-9">
            <main>
                <div class="card">
                    <div class="card-body p-4">
                        <div class="row justify-content-center">
                            <div class="col-6">
                                <h3>Update community</h3>

                                <form id="form" method="POST"
                                      action="${pageContext.request.contextPath}/community/${communityTransfer.commID}/update"
                                      enctype="multipart/form-data">
                                    <input type="hidden" name="commID" value="${communityTransfer.commID}">
                                    <div class="input-community input-community-sm mb-3">
                                        <div class="input-community-prepend">
                                            <span class="input-community-text">Community Name</span>
                                        </div>
                                        <input type="text" id="communityName" class="form-control" name="communityName"
                                               value="${communityTransfer.communityName}">
                                        <small class="form-text text-danger input-error"></small>
                                    </div>
                                    <div class="input-community mb-3">
                                        <div class="input-community-prepend">
                                            <span class="input-community-text">Community Description</span>
                                        </div>
                                        <input type="text" id="commDescription" class="form-control" name="commDescription"
                                               value="${communityTransfer.commDescription}">
                                    </div>
                                    <input type="hidden" name="picAttached" value="${communityTransfer.picAttached}">
                                    <br>
                                    <div class="form-community">
                                        <label for="picture">Upload community picture</label>
                                        <input type="file" class="form-control-file" id="picture"
                                               name="picture" value="${communityTransfer.picture}">
                                    </div>
                                    <br>
                                    <button type="submit" id="submitButton" class="btn btn-info" name="Save"
                                            value="Save">
                                        Update
                                    </button>
                                        <a class="btn btn-warning text-decoration-none" href="<c:url value="/community/${communityTransfer.commID}"/>">
                                            Cancel
                                        </a>
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