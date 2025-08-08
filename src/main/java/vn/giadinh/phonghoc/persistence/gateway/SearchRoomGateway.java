package vn.giadinh.phonghoc.persistence.gateway;

import vn.giadinh.phonghoc.dto.RoomDTO;

import java.sql.SQLException;
import java.util.List;

public interface SearchRoomGateway {
    List<RoomDTO> searchRooms(String searchTerm, String buildingFilter, String statusFilter, String lightFilter)
            throws SQLException;
}
