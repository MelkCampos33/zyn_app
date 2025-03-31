package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.User;

public class UserDatabase {

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO db (name, age, weight, height, imc) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getAge());
            stmt.setDouble(3, user.getWeight());
            stmt.setDouble(4, user.getHeight());
            stmt.setDouble(5, user.CalculateImc());
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> listUsers() throws SQLException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM db";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setName(rs.getString("name"));
                        user.setAge(rs.getInt("age"));
                        user.setWeight(rs.getDouble("weight"));
                        user.setHeight(rs.getDouble("height"));
                        user.setImc(rs.getDouble("imc"));
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }
    public boolean removeUser(String name) throws SQLException {
        String sql = "DELETE FROM db WHERE name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            int rowsAffected = stmt.executeUpdate(); // Retorna quantas linhas foram afetadas
            return rowsAffected > 0;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro ao conectar ao banco.", e);
        }
    }
}
