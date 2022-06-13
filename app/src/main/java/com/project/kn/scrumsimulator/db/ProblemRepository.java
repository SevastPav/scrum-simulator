package com.project.kn.scrumsimulator.db;

import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.entity.ProblemEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProblemRepository implements Repository<ProblemEntity> {

    private Connection connection;

    public ProblemRepository(DatabaseConfig dbConfig) {
//        this.connection = dbConfig.getExtraConnection();
    }

    public List<ProblemEntity> findAllByProjectId(int projectId) {

        ArrayList<ProblemEntity> problemEntities = new ArrayList<>();
        String sql = "SELECT * FROM problem where project_id = " + projectId;

        try (Statement statement = DatabaseConfig.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int groupNumber = resultSet.getInt("group_number");

                ProblemEntity problemEntity = new ProblemEntity(id, name, description, groupNumber, projectId);

                problemEntities.add(problemEntity);
            }

        } catch (SQLException ex) {
        }

        return problemEntities;
    }

    @Override
    public Optional<ProblemEntity> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<ProblemEntity> findAll() {
        return null;
    }

    @Override
    public ProblemEntity save(ProblemEntity problemEntity) {
        return null;
    }
}
