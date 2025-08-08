package vn.giadinh.phonghoc.persistence.dao;

import vn.giadinh.phonghoc.dto.UserDTO;
import vn.giadinh.phonghoc.persistence.gateway.AuthGateway;
import vn.giadinh.phonghoc.shared.utils.PasswordUtils;

import java.sql.*;
import java.util.Date;

public class AuthDAO implements AuthGateway {

    @Override
    public UserDTO authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND is_active = TRUE";

        try (Connection conn = InitializeDAO.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");

                // Verify password
                if (PasswordUtils.verifyPassword(password, storedHash)) {
                    return mapResultSetToUserDTO(rs);
                }
            }

            return null; // Authentication failed
        }
    }

    @Override
    public UserDTO registerUser(UserDTO user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password_hash, full_name, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = InitializeDAO.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, PasswordUtils.hashPassword(user.getPasswordHash())); // user.getPasswordHash() contains
                                                                                   // plain password
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getRole() != null ? user.getRole() : "USER");

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                    return user;
                }
            }

            return null;
        }
    }

    @Override
    public boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = InitializeDAO.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;
        }
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = InitializeDAO.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;
        }
    }

    private UserDTO mapResultSetToUserDTO(ResultSet rs) throws SQLException {
        UserDTO user = new UserDTO();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        user.setIsActive(rs.getBoolean("is_active"));
        return user;
    }
}
