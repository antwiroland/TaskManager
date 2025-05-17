<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Task Manager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <div class="container">
        <h1>Task Manager</h1>

        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/tasks/new" class="button">Add New Task</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="task" items="${taskList}">
                    <tr>
                        <td><c:out value="${task.id}" /></td>
                        <td><c:out value="${task.name}" /></td>
                        <td><c:out value="${task.status}" /></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/tasks/edit?id=${task.id}" class="button edit">Edit</a>
                            <a href="${pageContext.request.contextPath}/tasks/delete?id=${task.id}" class="button delete">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>