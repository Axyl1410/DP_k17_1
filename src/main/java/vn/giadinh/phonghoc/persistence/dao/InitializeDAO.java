package vn.giadinh.phonghoc.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InitializeDAO {
    public static Connection getConnection() throws SQLException {
        String username = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/rooms?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(url, username, password);
    }
}
