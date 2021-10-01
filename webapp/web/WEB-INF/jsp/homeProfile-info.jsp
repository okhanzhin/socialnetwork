<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Profile"/>
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
                <div class="card mb-3">
                    <h5 class="card-header"><c:out
                            value="${homeAccount.surname} ${homeAccount.name}
                            ${homeAccount.middlename}"/></h5>

                    <div class="card-body">
                        <table class="table table-borderless">
                            <tbody>
                            <c:if test="${!empty homeAccount.dateOfBirth}">
                                <tr>
                                    <th scope="row">Birthday</th>
                                    <td><c:out value="${homeAccount.dateOfBirth}"/></td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty homeAccount.homeAddress}">
                                <tr>
                                    <th scope="row">Home Address</th>
                                    <td><c:out value="${homeAccount.homeAddress}"/></td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                        <input id="toggle-additional-info" type="checkbox">
                        <label for="toggle-additional-info" class="toggle-additional-info">
                        </label>
                        <hr class="my-4">
                        <div class="additional-info">
                            <table class="table table-borderless">
                                <tbody>
                                <c:if test="${!empty homeAccount.workAddress}">
                                <tr>
                                    <th scope="row">Work Address</th>
                                    <td><c:out value="${homeAccount.workAddress}"/></td>
                                </tr>
                                </c:if>
                                <tr>
                                    <th scope="row">Email</th>
                                    <td><c:out value="${homeAccount.email}"/></td>
                                </tr>

                                <c:if test="${!empty homeAccount.phones}">
                                <c:forEach var="phone" items="${homeAccount.phones}">
                                <tr>
                                    <th scope="row"><c:out value="${phone.phoneType == 'work' ? 'Work' : 'Home'}"/>
                                        Phone
                                    </th>
                                    <td><c:out value="${phone.phoneNumber}"/></td>
                                </tr>
                                </c:forEach>
                                </c:if>
                            </table>
                            <hr class="my-4">
                            <table class="table table-borderless">
                                <tbody>
                                <c:if test="${!empty homeAccount.skype}">
                                    <tr>
                                        <th scope="row">Skype</th>
                                        <td><c:out value="${homeAccount.skype}"/></td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty homeAccount.icq}">
                                    <tr>
                                        <th scope="row">Icq</th>
                                        <td><c:out value="${homeAccount.icq}"/></td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty homeAccount.addInfo}">
                                    <tr>
                                        <th scope="row">About</th>
                                        <td><c:out value="${homeAccount.addInfo}"/></td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="card border-secondary mb-3">
                    <div class="card-body">
                        <form method="POST"
                              action="${pageContext.request.contextPath}/account/${homeAccount.accountID}/publish">
                            <input type="hidden" name="postType" value="user">

                            <div class="form-community mb-1">
                                <textarea class="form-control" id="exampleFormControlTextarea1" name="content"
                                          oninput='this.style.height = "";this.style.height = this.scrollHeight + "px"'
                                          onfocusout="this.value == '' ? this.style.height='' : this.style.height=this.scrollHeight+'px'"
                                          rows="1"></textarea>
                            </div>
                            <div class="text-right">
                                <button type="submit" class="btn-sm btn-info ml-auto">Post</button>
                            </div>
                        </form>
                    </div>
                </div>

                <c:if test="${!empty accountPosts}">
                    <div class="card">
                        <h5 class="card-header mb-3">Posts</h5>
                        <c:forEach var="post" items="${accountPosts}">
                            <div class="card-body item-list">
                                <div class="card">
                                    <div class="card-header">
                                        <c:out value="${post.creator.surname} ${post.creator.name}"/>
                                    </div>
                                    <div class="card-body card-inner-element">
                                        <blockquote class="blockquote mb-0">
                                            <p><c:out value="${post.postObject.content}"/></p>
                                            <footer class="blockquote-footer"><cite title="Publication Date">
                                                    ${post.publicationDateString}</cite></footer>
                                        </blockquote>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </main>
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp" %>