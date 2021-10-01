<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>

<jsp:include page="templates/head.jsp">
    <jsp:param name="pageTitle" value="Login"/>
</jsp:include>

<div class="container mt-auto">
    <div class="row justify-content-center align-items-center">
        <div class="login-form">
            <h1 class="main-label justify-content-center">
                <span>SKETCH NETWORK</span>
            </h1>

            <div class="card p-4">
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="form-community">
                        <label for="email">Email address</label>
                        <input type="email" class="form-control" id="email" name="email"
                               value="${email}" aria-describedby="emailHelp">
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                            else.</small>
                    </div>
                    <div class="form-community mb-1">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password"
                               value="${password}">
                    </div>
                    <div class="form-community form-check mb-2">
                        <input type="checkbox" class="form-check-input" id="remember-me" name="remember-me">
                        <label class="form-check-label" for="remember-me">Check me out</label>
                    </div>

                    <c:if test="${requestScope.logEmailPasError != null}">
                        <div class="alert alert-danger">
                            <c:out value="${requestScope.logEmailPasError}"/>
                        </div>
                    </c:if>
                    <c:if test="${requestScope.message != null}">
                        <div class="alert alert-success">
                            <c:out value="${requestScope.message}"/>
                        </div>
                    </c:if>

                    <button type="submit" class="btn btn-info" name="Save" value="Save">
                        Sign In
                    </button>
                    <button type="button" class="btn btn-warning ">
                        <a class="text-decoration-none" href="<c:url value="/account/new"/>" style="color:inherit">
                            Sign Up
                        </a>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp" %>
