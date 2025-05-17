<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>NovaTech Solutions - Task Manager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<div class="container">
    <h1>Task Manager</h1>

    <div class="header-actions">
        <a href="${pageContext.request.contextPath}/tasks/new" class="button">Add New Task</a>
    </div>

    <!-- Filter and Sort Form -->
    <form method="get" action="${pageContext.request.contextPath}/tasks" style="margin-bottom: 1.5rem;">
        <label for="status">Filter by Status:</label>
        <select name="status" id="status">
            <option value="">All</option>
            <option value="Not Started" ${selectedStatus == 'Not Started' ? 'selected' : ''}>Not Started</option>
            <option value="In Progress" ${selectedStatus == 'In Progress' ? 'selected' : ''}>In Progress</option>
            <option value="Completed" ${selectedStatus == 'Completed' ? 'selected' : ''}>Completed</option>
        </select>

        <label for="sort">Sort by Due Date:</label>
        <select name="sort" id="sort">
            <option value="">None</option>
            <option value="asc" ${selectedSort == 'asc' ? 'selected' : ''}>Ascending</option>
            <option value="desc" ${selectedSort == 'desc' ? 'selected' : ''}>Descending</option>
        </select>

        <button type="submit" class="button">Apply</button>
    </form>

    <!-- Task Table -->
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Status</th>
            <th>Due Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="task" items="${taskList}">
            <tr>
                <td><c:out value="${task.id}" /></td>
                <td><c:out value="${task.name}" /></td>
                <td><c:out value="${task.status}" /></td>
                <td><fmt:formatDate value="${task.dueDate}" pattern="yyyy-MM-dd" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/tasks/edit?id=${task.id}" class="button edit">Edit</a>
                    <a href="${pageContext.request.contextPath}/tasks/delete?id=${task.id}" class="button delete"
                       onclick="return confirm('Are you sure you want to delete this task?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty taskList}">
        <p>No tasks found.</p>
    </c:if>
</div>
</body>
</html>
