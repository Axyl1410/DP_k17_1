package vn.giadinh.phonghoc.persistence.gateway;

import vn.giadinh.phonghoc.dto.RoomDTO;

import java.sql.SQLException;

public interface EditRoomGateway {
    void updateRoom(RoomDTO room) throws SQLException;
}
