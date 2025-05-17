<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <div class="container">
        <h1>Error Occurred</h1>
        <div class="error-message">
            <c:choose>
                <c:when test="${not empty error}">
                    <p>${error}</p>
                </c:when>
                <c:otherwise>
                    <p>An unexpected error occurred.</p>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/tasks" class="button primary">Return to Task List</a>
        </div>
    </div>
</body>
</html>