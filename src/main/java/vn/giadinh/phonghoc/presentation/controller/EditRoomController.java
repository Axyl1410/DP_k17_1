package vn.giadinh.phonghoc.presentation.controller;

import vn.giadinh.phonghoc.business.usecase.EditRoomUsecase;
import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.dto.UpdateRoomDTO;
import vn.giadinh.phonghoc.presentation.model.EditRoomModel;

public class EditRoomController {
    public static StatusDTO statusDTO = new StatusDTO();
    public static EditRoomModel editRoomModel = new EditRoomModel();

    public static StatusDTO execute(RoomDTO roomDTO) {
        try {
            statusDTO = EditRoomUsecase.execute(roomDTO);
            editRoomModel.statusDTO = statusDTO;
        } catch (Exception e) {
            statusDTO.setStatus(vn.giadinh.phonghoc.shared.enums.StatusCode.FAILURE);
            statusDTO.setMessage(e.getMessage());
            editRoomModel.statusDTO = statusDTO;
        }
        editRoomModel.notifySubscribers();
        return statusDTO;
    }

    public static StatusDTO execute(UpdateRoomDTO updateRoomDTO) {
        try {
            // Convert UpdateRoomDTO to RoomDTO
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomId(updateRoomDTO.getRoomId());
            roomDTO.setBuildingBlock(updateRoomDTO.getBuildingBlock());
            roomDTO.setArea(updateRoomDTO.getArea());
            roomDTO.setNumLightBulbs(updateRoomDTO.getNumLightBulbs());
            roomDTO.setStartDateOfOperation(updateRoomDTO.getStartDateOfOperation());
            roomDTO.setRoomType(updateRoomDTO.getRoomType());
            roomDTO.setHasProjector(updateRoomDTO.getHasProjector());
            roomDTO.setNumComputers(updateRoomDTO.getNumComputers());
            roomDTO.setSpecialization(updateRoomDTO.getSpecialization());
            roomDTO.setCapacity(updateRoomDTO.getCapacity());
            roomDTO.setHasSink(updateRoomDTO.getHasSink());

            statusDTO = EditRoomUsecase.execute(roomDTO);
            editRoomModel.statusDTO = statusDTO;
        } catch (Exception e) {
            statusDTO.setStatus(vn.giadinh.phonghoc.shared.enums.StatusCode.FAILURE);
            statusDTO.setMessage(e.getMessage());
            editRoomModel.statusDTO = statusDTO;
        }
        editRoomModel.notifySubscribers();
        return statusDTO;
    }
}
