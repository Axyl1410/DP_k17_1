package vn.giadinh.phonghoc.persistence.dao;

import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.persistence.gateway.EditRoomGateway;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditRoomDAO implements EditRoomGateway {
    private final Connection connection;

    public EditRoomDAO() throws SQLException {
        this.connection = (Connection) InitializeDAO.GetConnection();
    }

    @Override
    public void updateRoom(RoomDTO room) throws SQLException {
        // First check if room exists
        String checkSql = "SELECT COUNT(*) FROM rooms WHERE room_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, room.roomId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new SQLException("Phòng học với mã " + room.roomId + " không tồn tại.");
            }
        }

        // Update the room
        String updateSql = """
                UPDATE rooms SET
                    room_type = ?, building_block = ?, area_sqm = ?, num_light_bulbs = ?,
                    start_operation_date = ?, has_projector = ?, num_computers = ?,
                    specialization = ?, capacity = ?, has_sink = ?
                WHERE room_id = ?""";

        try (PreparedStatement stmt = connection.prepareStatement(updateSql)) {
            stmt.setString(1, room.roomType);
            stmt.setString(2, room.buildingBlock);
            stmt.setObject(3, room.area);
            stmt.setObject(4, room.numLightBulbs);
            stmt.setDate(5, room.startDateOfOperation != null ? new Date(room.startDateOfOperation.getTime()) : null);
            stmt.setObject(6, room.hasProjector);
            stmt.setObject(7, room.numComputers);
            stmt.setString(8, room.specialization);
            stmt.setObject(9, room.capacity);
            stmt.setObject(10, room.hasSink);
            stmt.setString(11, room.roomId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Không thể cập nhật phòng học với mã " + room.roomId);
            }
        }
    }
}
