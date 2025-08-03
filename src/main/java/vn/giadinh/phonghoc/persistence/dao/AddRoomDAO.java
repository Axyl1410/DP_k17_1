package vn.giadinh.phonghoc.persistence.dao;

import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.persistence.gateway.AddRoomGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class AddRoomDAO implements AddRoomGateway {
    private final Connection connection = (Connection) InitializeDAO.GetConnection();

    public AddRoomDAO() throws SQLException {
    }

    @Override
    public void addRoom(RoomDTO room) {
    }
}
