package vn.giadinh.phonghoc.presentation.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.giadinh.phonghoc.business.usecase.AddRoomUsecase;
import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.presentation.model.AddRoomModel;
import vn.giadinh.phonghoc.persistence.gateway.AddRoomGateway;
import vn.giadinh.phonghoc.shared.enums.StatusCode;

@Getter
@RequiredArgsConstructor
public class AddRoomController {
    private AddRoomModel addRoomModel;
    private AddRoomUsecase addRoomUsecase;

    public AddRoomController(AddRoomModel model, AddRoomGateway gateway) {
        this.addRoomModel = model;
        this.addRoomUsecase = new AddRoomUsecase(gateway, new StatusDTO());
    }

    public void executeAddRoom(RoomDTO roomDTO) {
        try {
            // Validate input data
            if (roomDTO == null) {
                StatusDTO errorStatus = new StatusDTO();
                errorStatus.setStatus(StatusCode.FAILURE);
                errorStatus.setMessage("Dữ liệu phòng học không được để trống");
                addRoomModel.setStatus(errorStatus);
                return;
            }

            // Execute use case
            StatusDTO result = addRoomUsecase.execute(roomDTO);
            addRoomModel.setStatus(result);

            if (result.getStatus() == StatusCode.SUCCESS) {
                addRoomModel.clearData();
            }
        } catch (Exception e) {
            StatusDTO errorStatus = new StatusDTO();
            errorStatus.setStatus(StatusCode.FAILURE);
            errorStatus.setMessage("Lỗi xử lý: " + e.getMessage());
            addRoomModel.setStatus(errorStatus);
        }
    }

    public void clearForm() {
        addRoomModel.clearData();
    }

    /**
     * Update specific room field and validate form
     * This method was moved from AddRoomModel to follow 3-layer architecture
     */
    public void updateRoomField(String fieldName, Object value) {
        RoomDTO currentRoomData = addRoomModel.getRoomData();

        // Update field through UseCase (Business Layer)
        addRoomUsecase.updateRoomField(currentRoomData, fieldName, value);

        // Update model with new data
        addRoomModel.setRoomData(currentRoomData);

        // Validate form through UseCase (Business Layer)
        boolean isValid = addRoomUsecase.validateFormData(currentRoomData);
        addRoomModel.setFormValid(isValid);
    }

    public boolean validateRoomData(RoomDTO roomDTO) {
        if (roomDTO == null) {
            return false;
        }

        // Basic validation
        if (roomDTO.roomId == null || roomDTO.roomId.trim().isEmpty()) {
            return false;
        }

        if (roomDTO.buildingBlock == null || roomDTO.buildingBlock.trim().isEmpty()) {
            return false;
        }

        if (roomDTO.area == null || roomDTO.area <= 0) {
            return false;
        }

        if (roomDTO.numLightBulbs == null || roomDTO.numLightBulbs < 0) {
            return false;
        }

        if (roomDTO.startDateOfOperation == null) {
            return false;
        }
        return roomDTO.roomType != null && !roomDTO.roomType.trim().isEmpty();
    }
}
