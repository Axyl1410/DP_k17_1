package vn.giadinh.phonghoc.persistence.gateway;

import vn.giadinh.phonghoc.dto.RoomDTO;

import java.sql.SQLException;
import java.util.List;

public interface ViewRoomGateway {
    List<RoomDTO> getAll() throws SQLException;
}
