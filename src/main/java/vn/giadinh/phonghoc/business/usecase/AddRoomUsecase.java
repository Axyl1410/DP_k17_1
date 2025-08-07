package vn.giadinh.phonghoc.business.usecase;

import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.persistence.dao.AddRoomDAO;
import vn.giadinh.phonghoc.shared.enums.RoomType;
import vn.giadinh.phonghoc.shared.enums.StatusCode;

import java.sql.SQLException;
import java.util.Date;

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
            validateRoom(roomDTO);
            addRoomDAO.addRoom(roomDTO);
            statusDTO.setStatus(StatusCode.SUCCESS);
            statusDTO.setMessage("Room added successfully");
        } catch (IllegalArgumentException | SQLException e) {
            statusDTO.setStatus(StatusCode.FAILURE);
            statusDTO.setMessage(e.getMessage());
        }
        return statusDTO;
    }

    private static void validateRoom(RoomDTO dto) throws IllegalArgumentException {
        if (dto == null) {
            throw new IllegalArgumentException("Dữ liệu phòng học không được để trống.");
        }
        if (dto.getRoomId() == null || dto.getRoomId().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được để trống.");
        }
        if (!dto.getRoomId().matches("^[A-Z0-9-]{3,15}$")) {
            throw new IllegalArgumentException(
                    "Mã phòng phải từ 3-15 ký tự, chỉ bao gồm chữ hoa, số và dấu gạch ngang (-).");
        }
        if (dto.getBuildingBlock() == null || dto.getBuildingBlock().trim().isEmpty()) {
            throw new IllegalArgumentException("Tòa nhà không được để trống.");
        }
        if (dto.getArea() <= 0) {
            throw new IllegalArgumentException("Diện tích phải là một số dương.");
        }
        if (dto.getNumLightBulbs() < 0) {
            throw new IllegalArgumentException("Số bóng đèn không thể là số âm.");
        }
        if (dto.getStartDateOfOperation() == null) {
            throw new IllegalArgumentException("Ngày đưa vào hoạt động không được để trống.");
        }
        if (dto.getStartDateOfOperation().after(new Date())) {
            throw new IllegalArgumentException("Ngày đưa vào hoạt động không thể là một ngày trong tương lai.");
        }
        if (dto.getRoomType() == null || dto.getRoomType().trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn một loại phòng.");
        }
        // Validate room type using enum
        RoomType roomType = RoomType.fromCode(dto.getRoomType());
        if (roomType == null) {
            throw new IllegalArgumentException("Loại phòng không hợp lệ: " + dto.getRoomType());
        }
        roomType.validateRoomData(dto);
    }
}
