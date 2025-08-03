package vn.giadinh.phonghoc.persistence.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

public class InitializeDAO {
    public static Object GetConnection() throws SQLException {
        String username = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/rooms?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(url, username, password);
    }
}
