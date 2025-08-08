package vn.giadinh.phonghoc.persistence.dao;

import vn.giadinh.phonghoc.persistence.gateway.DeleteRoomGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteRoomDAO implements DeleteRoomGateway {
    private final Connection connection;

    public DeleteRoomDAO() throws SQLException {
        this.connection = (Connection) InitializeDAO.GetConnection();
    }

    @Override
    public void deleteRoom(String roomId) throws SQLException {
        // First check if room exists
        String checkSql = "SELECT COUNT(*) FROM rooms WHERE room_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, roomId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new SQLException("Phòng học với mã " + roomId + " không tồn tại.");
            }
        }

        // Delete the room
        String deleteSql = "DELETE FROM rooms WHERE room_id = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
            deleteStmt.setString(1, roomId);
            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Không thể xóa phòng học với mã " + roomId);
            }
        }
    }
}
