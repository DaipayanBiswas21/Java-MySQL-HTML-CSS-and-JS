package com.earthmonitor.dao;

import com.earthmonitor.exception.MyException;
import com.earthmonitor.model.User;
import com.earthmonitor.util.DB;

import java.sql.*;

public class UserDAO {
    public User findByUsername(String username) throws MyException {
        String sql = "SELECT id, username, password_hash, role FROM users WHERE username = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        User.Role.valueOf(rs.getString("role"))
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            throw new MyException("Failed to query user", e);
        }
    }

    public void createUser(String username, String passwordHash, User.Role role) throws MyException {
        String sql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setString(3, role.name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new MyException("Failed to create user: " + e.getMessage(), e);
        }
    }
}
