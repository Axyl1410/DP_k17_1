package vn.giadinh.phonghoc.persistence.gateway;

import vn.giadinh.phonghoc.dto.UserDTO;

import java.sql.SQLException;

public interface AuthGateway {
    UserDTO authenticateUser(String username, String password) throws SQLException;

    UserDTO registerUser(UserDTO user) throws SQLException;

    boolean isUsernameExists(String username) throws SQLException;

    boolean isEmailExists(String email) throws SQLException;
}
