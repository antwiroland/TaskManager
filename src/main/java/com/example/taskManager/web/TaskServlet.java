package com.example.taskManager.web;

import com.example.taskManager.dao.TaskDao;
import com.example.taskManager.model.Task;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "TaskController", urlPatterns = {
        "/tasks",
        "/tasks/new",
        "/tasks/insert",
        "/tasks/delete",
        "/tasks/edit",
        "/tasks/update"
})
public class TaskServlet extends HttpServlet {
    private TaskDao taskDao;

    @Override
    public void init() {
        taskDao = new TaskDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/tasks/new":
                    showNewForm(request, response);
                    break;
                case "/tasks/insert":
                    insertTask(request, response);
                    break;
                case "/tasks/delete":
                    deleteTask(request, response);
                    break;
                case "/tasks/edit":
                    showEditForm(request, response);
                    break;
                case "/tasks/update":
                    updateTask(request, response);
                    break;
                case "/tasks":
                default:
                    listAllTasks(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException("Database error occurred", ex);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid task ID format");
        }
    }

    private void listAllTasks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Task> taskList = taskDao.getAllTasks();
        request.setAttribute("taskList", taskList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/task-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("task", null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/task-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Task existingTask = taskDao.getTaskById(id);
        if (existingTask == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            return;
        }
        request.setAttribute("task", existingTask);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/task-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String status = request.getParameter("status");

        if (name == null || name.trim().isEmpty() || status == null || status.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task name and status are required");
            return;
        }

        Task newTask = new Task(name.trim(), status.trim());
        taskDao.insertTask(newTask);
        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    private void updateTask(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String status = request.getParameter("status");

        if (name == null || name.trim().isEmpty() || status == null || status.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task name and status are required");
            return;
        }

        Task task = new Task(id, name.trim(), status.trim());
        if (!taskDao.updateTask(task)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (!taskDao.deleteTask(id)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/tasks");
    }
}