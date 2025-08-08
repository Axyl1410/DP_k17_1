package vn.giadinh.phonghoc.persistence.dao;

import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.persistence.gateway.SearchRoomGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchRoomDAO implements SearchRoomGateway {
    private final Connection connection;

    public SearchRoomDAO() throws SQLException {
        this.connection = (Connection) InitializeDAO.GetConnection();
    }

    @Override
    public List<RoomDTO> searchRooms(String searchTerm, String buildingFilter, String statusFilter, String lightFilter)
            throws SQLException {
        List<RoomDTO> roomsDTO = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM rooms WHERE 1=1");
        List<Object> parameters = new ArrayList<>();
        int paramIndex = 1;

        // Add search term filter
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append(" AND (room_id LIKE ? OR building_block LIKE ? OR room_type LIKE ?)");
            String likePattern = "%" + searchTerm.trim() + "%";
            parameters.add(likePattern);
            parameters.add(likePattern);
            parameters.add(likePattern);
            paramIndex += 3;
        }

        // Add building filter
        if (buildingFilter != null && !buildingFilter.trim().isEmpty() && !buildingFilter.equals("Tất cả")) {
            sql.append(" AND building_block = ?");
            parameters.add(buildingFilter.trim());
            paramIndex++;
        }

        // Add status filter (standard/non-standard)
        if (statusFilter != null && !statusFilter.trim().isEmpty() && !statusFilter.equals("Tất cả")) {
            if (statusFilter.equals("Đạt chuẩn")) {
                sql.append(" AND (area_sqm >= 50 AND num_light_bulbs >= 10)");
            } else if (statusFilter.equals("Không đạt chuẩn")) {
                sql.append(" AND (area_sqm < 50 OR num_light_bulbs < 10)");
            }
        }

        // Add light filter (sufficient/insufficient light)
        if (lightFilter != null && !lightFilter.trim().isEmpty() && !lightFilter.equals("Tất cả")) {
            if (lightFilter.equals("Đủ ánh sáng")) {
                sql.append(" AND num_light_bulbs >= 10");
            } else if (lightFilter.equals("Thiếu ánh sáng")) {
                sql.append(" AND num_light_bulbs < 10");
            }
        }

        sql.append(" ORDER BY room_id");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            // Set parameters
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = stmt.executeQuery();
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
        return roomsDTO;
    }
}
