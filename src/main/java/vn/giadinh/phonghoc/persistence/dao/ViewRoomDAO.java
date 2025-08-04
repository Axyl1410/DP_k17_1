package vn.giadinh.phonghoc.persistence.dao;

import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.persistence.gateway.ViewRoomGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRoomDAO implements ViewRoomGateway {
    private final Connection connection = (Connection) InitializeDAO.GetConnection();

    public ViewRoomDAO() throws SQLException {
    }

    // For test connection only
    public static void main(String[] args) {
        try {
            ViewRoomDAO dao = new ViewRoomDAO();
            List<RoomDTO> roomDTOS = dao.getAll();
            dao.PrintAll(roomDTOS);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public List<RoomDTO> getAll() throws SQLException {
        List<RoomDTO> roomsDTO = new ArrayList<>();
        String sql = "SELECT * FROM rooms ORDER BY room_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                RoomDTO dto = new RoomDTO();
                dto.setRoomId(rs.getString("room_id"));
                dto.setRoomType(rs.getString("room_type"));
                dto.setBuildingBlock(rs.getString("building_block"));
                dto.setArea(rs.getDouble("area_sqm"));
                dto.setNumLightBulbs(rs.getInt("num_light_bulbs"));
                dto.setStartDateOfOperation(rs.getDate("start_operation_date"));
                dto.setHasProjector(rs.getBoolean("has_projector"));
                dto.setNumComputers(rs.getInt("num_computers"));
                dto.setCapacity(rs.getInt("capacity"));
                dto.setHasSink(rs.getBoolean("has_sink"));
                roomsDTO.add(dto);
            }
        }
        connection.close();
        return roomsDTO;
    }

    // For test connection only
    private void PrintAll(List<RoomDTO> roomDTOS) {
        for (RoomDTO roomDTO : roomDTOS) {
            System.out.println(roomDTO);
        }
    }
}
