package vn.giadinh.phonghoc.business.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.persistence.gateway.AddRoomGateway;
import vn.giadinh.phonghoc.shared.enums.RoomType;
import vn.giadinh.phonghoc.shared.enums.StatusCode;

import java.sql.SQLException;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoomUsecase {
    private AddRoomGateway addRoomGateway;
    private StatusDTO statusDTO;

    public boolean validateFormData(RoomDTO roomData) {
        if (roomData == null) {
            return false;
        }
        return roomData.roomId != null && !roomData.roomId.trim().isEmpty() &&
                roomData.buildingBlock != null && !roomData.buildingBlock.trim().isEmpty() &&
                roomData.area != null && roomData.area > 0 &&
                roomData.numLightBulbs != null && roomData.numLightBulbs >= 0 &&
                roomData.startDateOfOperation != null &&
                roomData.roomType != null && !roomData.roomType.trim().isEmpty();
    }

    public void updateRoomField(RoomDTO roomData, String fieldName, Object value) {
        if (roomData == null) {
            return;
        }
        switch (fieldName) {
            case "roomId":
                roomData.roomId = (String) value;
                break;
            case "roomType":
                roomData.roomType = (String) value;
                break;
            case "buildingBlock":
                roomData.buildingBlock = (String) value;
                break;
            case "area":
                roomData.area = (Double) value;
                break;
            case "numLightBulbs":
                roomData.numLightBulbs = (Integer) value;
                break;
            case "startDateOfOperation":
                roomData.startDateOfOperation = (java.util.Date) value;
                break;
            case "hasProjector":
                roomData.hasProjector = (Boolean) value;
                break;
            case "numComputers":
                roomData.numComputers = (Integer) value;
                break;
            case "specialization":
                roomData.specialization = (String) value;
                break;
            case "capacity":
                roomData.capacity = (Integer) value;
                break;
            case "hasSink":
                roomData.hasSink = (Boolean) value;
                break;
        }
    }

    private void validateRoom(RoomDTO dto) throws IllegalArgumentException {
        if (dto == null) {
            throw new IllegalArgumentException("Dữ liệu phòng học không được để trống.");
        }
        if (dto.getRoomId() == null || dto.getRoomId().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được để trống.");
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
        RoomType roomType = RoomType.fromCode(dto.getRoomType());
        if (roomType == null) {
            throw new IllegalArgumentException("Loại phòng không hợp lệ: " + dto.getRoomType());
        }
        roomType.validateRoomData(dto);
    }

    public StatusDTO execute(RoomDTO roomDTO) {
        try {
            validateRoom(roomDTO);
            addRoomGateway.addRoom(roomDTO);
            statusDTO.setStatus(StatusCode.SUCCESS);
            statusDTO.setMessage("Thêm phòng học thành công!");

        } catch (IllegalArgumentException e) {
            statusDTO.setStatus(StatusCode.FAILURE);
            statusDTO.setMessage("Lỗi dữ liệu: " + e.getMessage());
        } catch (SQLException e) {
            statusDTO.setStatus(StatusCode.FAILURE);
            statusDTO.setMessage("Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            statusDTO.setStatus(StatusCode.FAILURE);
            statusDTO.setMessage("Lỗi không xác định: " + e.getMessage());
        }
        return statusDTO;
    }

}
