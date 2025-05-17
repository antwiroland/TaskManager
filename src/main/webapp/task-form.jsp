<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${task != null}">Edit Task</c:when>
            <c:otherwise>Add New Task</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <div class="container">
        <h1>
            <c:choose>
                <c:when test="${task != null}">Edit Task</c:when>
                <c:otherwise>Add New Task</c:otherwise>
            </c:choose>
        </h1>

        <form action="${pageContext.request.contextPath}/tasks/<c:choose><c:when test='${task != null}'>update</c:when><c:otherwise>insert</c:otherwise></c:choose>" method="post">
            <c:if test="${task != null}">
                <input type="hidden" name="id" value="${task.id}" />
            </c:if>

            <div class="form-group">
                <label for="name">Task Name:</label>
                <input type="text" id="name" name="name" value="<c:out value='${task.name}' />" required />
            </div>

            <div class="form-group">
                <label for="status">Status:</label>
                <select id="status" name="status" required>
                    <option value="Not Started" <c:if test="${task.status == 'Not Started'}">selected</c:if>>Not Started</option>
                    <option value="In Progress" <c:if test="${task.status == 'In Progress'}">selected</c:if>>In Progress</option>
                    <option value="Completed" <c:if test="${task.status == 'Completed'}">selected</c:if>>Completed</option>
                </select>
            </div>

            <div class="form-group">
                <label for="dueDate">Due Date:</label>
                <input type="date" id="dueDate" name="dueDate"
                       value="<fmt:formatDate value='${task.dueDate}' pattern='yyyy-MM-dd' />" required />
            </div>

            <div class="form-actions">
                <button type="submit" class="button">Save</button>
                <a href="${pageContext.request.contextPath}/tasks" class="button cancel">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>
