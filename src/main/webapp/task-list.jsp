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
        <div class="filter-sort">
            <form method="get" action="${pageContext.request.contextPath}/tasks">
                <div>
                    <label for="status">Filter by Status:</label>
                    <select name="status" id="status">
                        <option value="">All</option>
                        <option value="Pending" ${param.status == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="Completed" ${param.status == 'Completed' ? 'selected' : ''}>Completed</option>
                    </select>
                </div>

                <div>
                    <label for="sort">Sort by:</label>
                    <select name="sort" id="sort">
                        <option value="">None</option>
                        <option value="dueDateAsc" ${param.sort == 'dueDateAsc' ? 'selected' : ''}>Due Date (Asc)</option>
                        <option value="dueDateDesc" ${param.sort == 'dueDateDesc' ? 'selected' : ''}>Due Date (Desc)</option>
                    </select>
                </div>

                <button type="submit" class="button">Apply</button>
                <a href="${pageContext.request.contextPath}/tasks" class="button cancel">Clear</a>
            </form>
        </div>

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
                        <td>
                            <fmt:formatDate value="${task.dueDate}" pattern="yyyy-MM-dd" />
                        </td>
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
