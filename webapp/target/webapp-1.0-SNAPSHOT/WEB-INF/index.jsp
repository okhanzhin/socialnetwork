<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>$Index$</title>
</head>
    <body>
<%--        <c:redirect url="${pageContext.request.contextPath}/auth/login"/>--%>
            <a href="<c:url value="${pageContext.request.contextPath}/auth/login"/>">click</a>
<%--        <c:redirect url='/auth/login'/>--%>
    </body>
</html>
