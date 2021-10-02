<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Community"/>
</jsp:include>

<%@include file="templates/navbar.jsp" %>

<div class="container mb-3" id="content">
    <div class="row">

        <jsp:include page="templates/community-sidebar.jsp">
            <jsp:param name="communityId" value="${sessionScope.activeCommunity.commID}"/>
            <jsp:param name="picAttached" value="${sessionScope.activeCommunity.picAttached}"/>
        </jsp:include>

        <c:set var="community" scope="page" value="${sessionScope.activeCommunity}"/>
        <c:set var="visitorStatus" scope="page" value="${sessionScope.commPermissions.get('visitorStatus')}"/>

        <div class="col-9">
            <main>
                <div class="card mb-3">
                    <h5 class="card-header"><c:out value="${community.communityName}"/></h5>
                    <div class="card-body">
                        <table class="table table-borderless">
                            <tbody>
                            <c:if test="${!empty community.commDescription}">
                                <tr>
                                    <th scope="row">Description</th>
                                    <td><c:out value="${community.commDescription}"/></td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>

                <c:if test="${sessionScope.commPermissions.get('isMember')}">
                    <div class="card border-secondary mb-3">
                        <div class="card-body">
                            <form method="POST"
                                  action="${pageContext.request.contextPath}/community/${sessionScope.activeCommunity.commID}/publish">
                                <input type="hidden" name="postType" value="community">

                                <div class="form-community">
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
                </c:if>

                <c:if test="${!empty commPosts}">
                    <div class="card">
                        <h5 class="card-header">Posts</h5>
                        <c:forEach var="post" items="${commPosts}">
                            <div class="card-body p-4">
                                <div class="card mb-3">
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