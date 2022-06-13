package com.project.kn.scrumsimulator.db;

import com.project.kn.scrumsimulator.MainActivity;
import com.project.kn.scrumsimulator.config.DatabaseConfig;
import com.project.kn.scrumsimulator.entity.EventEntity;
import com.project.kn.scrumsimulator.entity.PlayerEntity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerRepository implements Repository<PlayerEntity> {

    private Connection connection;

    public PlayerRepository(DatabaseConfig dbConfig) {
        this.connection = dbConfig.getExtraConnection();
    }

    public boolean verifyLoginAndPassword(String playerLogin, String playerPassword) {

        String sql = "SELECT * FROM player where login = " + playerLogin + " and password = " + playerPassword;

        try (Statement statement = DatabaseConfig.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Date createdAt = resultSet.getDate("created_at");

                MainActivity.loginedPlayer = new PlayerEntity(id, login, password, email, createdAt);

                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Optional<PlayerEntity> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<PlayerEntity> findAll() {
        return null;
    }

    @Override
    public PlayerEntity save(PlayerEntity playerEntity) {
        return null;
    }
}
