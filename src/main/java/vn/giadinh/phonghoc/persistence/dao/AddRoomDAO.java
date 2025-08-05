package vn.giadinh.phonghoc.persistence.dao;

import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.persistence.gateway.AddRoomGateway;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddRoomDAO implements AddRoomGateway {
    private final Connection connection;

    public AddRoomDAO() throws SQLException {
        this.connection = (Connection) InitializeDAO.GetConnection();
    }

    @Override
    public void addRoom(RoomDTO room) throws SQLException {
        String sql = """
                INSERT INTO rooms (
                    room_id, room_type, building_block, area_sqm, num_light_bulbs, start_operation_date,
                    has_projector, num_computers, specialization, capacity, has_sink
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, room.roomId);
            stmt.setString(2, room.roomType);
            stmt.setString(3, room.buildingBlock);
            stmt.setObject(4, room.area);
            stmt.setObject(5, room.numLightBulbs);
            stmt.setDate(6, room.startDateOfOperation != null ? new Date(room.startDateOfOperation.getTime()) : null);
            stmt.setObject(7, room.hasProjector);
            stmt.setObject(8, room.numComputers);
            stmt.setString(9, room.specialization);
            stmt.setObject(10, room.capacity);
            stmt.setObject(11, room.hasSink);
            stmt.executeUpdate();
        }
    }
}
