package com.project.kn.scrumsimulator.db;

import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.entity.TaskEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepository implements Repository<TaskEntity> {

    private Connection connection;

    public TaskRepository(DatabaseConfig dbConfig) {
//        this.connection = dbConfig.getExtraConnection();
    }

    public List<TaskEntity> findAllByProjectId(int projectId) {

        ArrayList<TaskEntity> taskEntities = new ArrayList<>();
        String sql = "SELECT * FROM task where project_id = " + projectId;

        try (Statement statement = DatabaseConfig.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int hours = resultSet.getInt("hours");
                int priority = resultSet.getInt("priority");

                TaskEntity taskEntity = new TaskEntity(id, name, description, hours, priority, projectId);

                taskEntities.add(taskEntity);
            }

        } catch (SQLException ex) {
        }

        return taskEntities;
    }

    @Override
    public Optional<TaskEntity> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<TaskEntity> findAll() {
        return null;
    }

    @Override
    public TaskEntity save(TaskEntity taskEntity) {
        return null;
    }
}
