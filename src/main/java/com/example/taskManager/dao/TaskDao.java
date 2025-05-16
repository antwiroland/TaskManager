package com.example.taskManager.dao;

import com.example.taskManager.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    private   String jdbcUrl = "";
    private   String jdbcUser = "root";
    private   String jdbcPassword = "banko1234";

    private static  final String INSERT_TASK = "INSERT INTO task " + "(name, status) VALUES " + "(?, ?);";
    private static  final String SELECT_ALL_TASK = "SELECT * FROM task;";
    private static  final String SELECT_TASK_BY_ID = "SELECT id, name, status FROM task WHERE id=?;";
    private static  final String UPDATE_TASK = "UPDATE task SET name=?, status=? WHERE id=?;";
    private static final String DELETE_TASK = "DELETE FROM tasks WHERE id = ?";

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

    public void insertTask(Task task) throws SQLException{
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK);
        ){
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean updateTask(Task task) throws SQLException{
        boolean updated = false;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK);
        ){
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getStatus());
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
                    task = new Task(id, name, status);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return task;
    }


    public List<Task> getAllTasks() throws SQLException{
        List<Task> tasks = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASK);
            ResultSet re = preparedStatement.executeQuery();
        ){
            while (re.next()){
                int id = re.getInt("id");
                String name = re.getString("name");
                String status = re.getString("status");
                tasks.add(new Task(id,name,status));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return tasks;
    }

    public boolean deleteTask(int id) throws SQLException{
        boolean deleted = false;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK);
        ){
            preparedStatement.setInt(1,id);
            deleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return deleted;
    }

}
