package vn.giadinh.phonghoc.presentation.controller;

import vn.giadinh.phonghoc.business.usecase.AddRoomUsecase;
import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.presentation.model.AddRoomModel;
import vn.giadinh.phonghoc.shared.enums.StatusCode;

public class addRoomController {
    public static StatusDTO statusDTO = new StatusDTO();
    public static AddRoomModel addRoomModel = new AddRoomModel();

    public static void execute(RoomDTO roomDTO) {
        try {
            statusDTO = AddRoomUsecase.execute(roomDTO);
            addRoomModel.statusDTO = statusDTO;
        } catch (Exception e) {
            statusDTO.setStatus(StatusCode.FAILURE);
            statusDTO.setMessage(e.getMessage());
            addRoomModel.statusDTO = statusDTO;
        }
    }
}
