package vn.giadinh.phonghoc.persistence.gateway;

import java.sql.SQLException;

public interface DeleteRoomGateway {
    void deleteRoom(String roomId) throws SQLException;
}
