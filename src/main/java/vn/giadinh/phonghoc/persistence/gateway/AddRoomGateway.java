package vn.giadinh.phonghoc.persistence.gateway;

import vn.giadinh.phonghoc.dto.RoomDTO;

import java.sql.SQLException;

public interface AddRoomGateway {
    void addRoom(RoomDTO room) throws SQLException;
}
