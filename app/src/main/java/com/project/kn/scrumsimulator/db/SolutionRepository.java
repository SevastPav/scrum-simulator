package com.project.kn.scrumsimulator.db;

import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.entity.SolutionEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SolutionRepository implements Repository<SolutionEntity> {

    private Connection connection;

    public SolutionRepository(DatabaseConfig dbConfig) {
//        this.connection = dbConfig.getExtraConnection();
    }

    public List<SolutionEntity> findAllByProjectId(int projectId) {

        ArrayList<SolutionEntity> solutionEntities = new ArrayList<>();
        String sql = "SELECT * FROM solution where project_id = " + projectId;

        try (Statement statement = DatabaseConfig.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int groupNumber = resultSet.getInt("group_number");

                SolutionEntity solutionEntity = new SolutionEntity(id, name, description, groupNumber, projectId);

                solutionEntities.add(solutionEntity);
            }

        } catch (SQLException ex) {
        }

        return solutionEntities;
    }

    @Override
    public Optional<SolutionEntity> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<SolutionEntity> findAll() {
        return null;
    }

    @Override
    public SolutionEntity save(SolutionEntity solutionEntity) {
        return null;
    }
}
