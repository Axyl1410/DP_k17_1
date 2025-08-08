package vn.giadinh.phonghoc.business.usecase;

import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.persistence.dao.DeleteRoomDAO;
import vn.giadinh.phonghoc.persistence.gateway.DeleteRoomGateway;
import vn.giadinh.phonghoc.shared.enums.StatusCode;

import java.sql.SQLException;

public class DeleteRoomUsecase {
    private static final DeleteRoomGateway GATEWAY;
    public static StatusDTO statusDTO = new StatusDTO();

    static {
        try {
            GATEWAY = new DeleteRoomDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static StatusDTO execute(String roomId) {
        try {
            validateRoomId(roomId);
            GATEWAY.deleteRoom(roomId);
            statusDTO.setStatus(StatusCode.SUCCESS);
            statusDTO.setMessage("Xóa phòng học thành công");
        } catch (IllegalArgumentException e) {
            statusDTO.setStatus(StatusCode.FAILURE);
            statusDTO.setMessage(e.getMessage());
        } catch (SQLException e) {
            statusDTO.setStatus(StatusCode.FAILURE);
            statusDTO.setMessage("Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            statusDTO.setStatus(StatusCode.FAILURE);
            statusDTO.setMessage("Lỗi không xác định: " + e.getMessage());
        }
        return statusDTO;
    }

    private static void validateRoomId(String roomId) throws IllegalArgumentException {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được để trống.");
        }
    }
}
