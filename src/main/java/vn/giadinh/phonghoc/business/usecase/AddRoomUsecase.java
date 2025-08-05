package vn.giadinh.phonghoc.business.usecase;

import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.persistence.dao.AddRoomDAO;
import vn.giadinh.phonghoc.shared.enums.StatusCode;

import java.sql.SQLException;

public class AddRoomUsecase {
    private static final AddRoomDAO addRoomDAO;
    public static StatusDTO statusDTO = new StatusDTO();

    static {
        try {
            addRoomDAO = new AddRoomDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static StatusDTO execute(RoomDTO roomDTO) {
        try {
            addRoomDAO.addRoom(roomDTO);
            statusDTO.setStatus(StatusCode.SUCCESS);
            statusDTO.setMessage("Room added successfully");
        } catch (SQLException e) {
            statusDTO.setStatus(StatusCode.FAILURE);
            statusDTO.setMessage(e.getMessage());
        }
        return statusDTO;
    }
}
