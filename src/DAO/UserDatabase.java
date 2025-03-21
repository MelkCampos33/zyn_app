package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.util.concurrent.CopyOnWriteArrayList;

import Model.User;

public class UserDatabase {
    public void addUser(User user) throws SQLException {
         String sql = "INSERT INTO users (name, age, weight, height, imc) VALUES (?, ?, ?, ?, ?)";

        public class UserDatabase {
            private List<User> users;

            public UserDatabase() {
                this.users = new ArrayList<>(); // iniciando o construtor
            }

            public void addUser(User user) {
                users.add(user);
            }

            public boolean removeUser(String name) {
                return users.removeIf(user -> user.getName().equals(name));
            }

            public List<User> listUsers() {
                return users;
            }
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setInt(2, Integer.parseInt(String.valueOf(user.getAge())));
            stmt.setDouble(3, user.getWeight());
            stmt.setDouble(4, user.getHeight());
            stmt.setDouble(5, user.CalculateImc());
            stmt.executeUpdate();
        }
    }

    public List<User> UserList() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {

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
        return users;
    }

}
