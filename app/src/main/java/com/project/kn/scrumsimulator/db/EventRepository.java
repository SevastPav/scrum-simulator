package com.project.kn.scrumsimulator.db;

import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.entity.EventEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventRepository implements Repository<EventEntity> {

    private Connection connection;

    public EventRepository(DatabaseConfig dbConfig) {
        this.connection = dbConfig.getExtraConnection();
    }

    public List<EventEntity> findAllByProjectId(int projectId) {

        ArrayList<EventEntity> problems = new ArrayList<>();
        String sql = "SELECT * FROM event where project_id = " + projectId;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int hoursCount = resultSet.getInt("hours_count");

                EventEntity eventEntity = new EventEntity(id, name, description, hoursCount, projectId);

                problems.add(eventEntity);
            }

        } catch (SQLException ex) {
        }

        return problems;
    }

    @Override
    public Optional<EventEntity> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<EventEntity> findAll() {
        return null;
    }

    @Override
    public EventEntity save(EventEntity eventEntity) {
        return null;
    }
}
