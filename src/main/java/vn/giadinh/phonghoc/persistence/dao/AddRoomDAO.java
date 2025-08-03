package vn.giadinh.phonghoc.persistence.dao;

import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.persistence.gateway.RoomGateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddRoomDAO implements RoomGateway {
    private final Connection connection;

    public AddRoomDAO() throws SQLException {
        String username = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/rooms?useSSL=false&serverTimezone=UTC";
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public void addRoom(RoomDTO room) {
    }
}
