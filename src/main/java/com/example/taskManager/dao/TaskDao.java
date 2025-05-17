package com.example.taskManager.dao;

import com.example.taskManager.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    private String jdbcUrl = "jdbc:mysql://localhost:3306/task_db";
    private String jdbcUser = "root";
    private String jdbcPassword = "banko1234";

    private static final String INSERT_TASK = "INSERT INTO task (name, status, due_date) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_TASK = "SELECT * FROM task";
    private static final String SELECT_TASK_BY_ID = "SELECT id, name, status, due_date FROM task WHERE id=?";
    private static final String UPDATE_TASK = "UPDATE task SET name=?, status=?, due_date=? WHERE id=?";
    private static final String DELETE_TASK = "DELETE FROM task WHERE id=?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void insertTask(Task task) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getStatus());
            preparedStatement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean updateTask(Task task) throws SQLException {
        boolean updated = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getStatus());
            preparedStatement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
            preparedStatement.setInt(4, task.getId());
            updated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return updated;
    }

    public Task getTaskById(int id) throws SQLException {
        Task task = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet re = preparedStatement.executeQuery()) {
                if (re.next()) {
                    String name = re.getString("name");
                    String status = re.getString("status");
                    Date dueDate = re.getDate("due_date");
                    task = new Task(id, name, status, dueDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return task;
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASK);
             ResultSet re = preparedStatement.executeQuery()) {
            while (re.next()) {
                int id = re.getInt("id");
                String name = re.getString("name");
                String status = re.getString("status");
                Date dueDate = re.getDate("due_date");
                tasks.add(new Task(id, name, status, dueDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return tasks;
    }

    public boolean deleteTask(int id) throws SQLException {
        boolean deleted = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK)) {
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return deleted;
    }

    public List<Task> getTasksFilteredAndSorted(String statusFilter, String sortOrder) throws SQLException {
        List<Task> tasks = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM task WHERE 1=1");

        if (statusFilter != null && !statusFilter.isEmpty()) {
            sql.append(" AND status = ?");
        }

        if ("asc".equalsIgnoreCase(sortOrder)) {
            sql.append(" ORDER BY due_date ASC");
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            sql.append(" ORDER BY due_date DESC");
        }

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (statusFilter != null && !statusFilter.isEmpty()) {
                stmt.setString(paramIndex++, statusFilter);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    Date dueDate = rs.getDate("due_date");
                    tasks.add(new Task(id, name, status, dueDate));
                }
            }
        }

        return tasks;
    }

}
