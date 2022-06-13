package com.project.kn.scrumsimulator.db;

import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.entity.ProjectEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepository implements Repository<ProjectEntity> {

    private Connection connection;

    public ProjectRepository(DatabaseConfig dbConfig) {
        this.connection = dbConfig.getExtraConnection();
    }

    @Override
    public Optional<ProjectEntity> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<ProjectEntity> findAll() {
        ArrayList<ProjectEntity> projectEntities = new ArrayList<>();
        String sql = "SELECT * FROM project";

        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                ProjectEntity projectEntity = new ProjectEntity(id, name, description);

                projectEntities.add(projectEntity);
            }

        } catch (SQLException ex) {
        }

        return projectEntities;
    }

    @Override
    public ProjectEntity save(ProjectEntity projectEntity) {
        return null;
    }
}
