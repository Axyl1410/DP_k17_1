package vn.giadinh.phonghoc.presentation.controller;

import vn.giadinh.phonghoc.business.usecase.DeleteRoomUsecase;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.presentation.model.DeleteRoomModel;

public class DeleteRoomController {
    public static StatusDTO statusDTO = new StatusDTO();
    public static DeleteRoomModel deleteRoomModel = new DeleteRoomModel();

    public static StatusDTO execute(String roomId) {
        try {
            statusDTO = DeleteRoomUsecase.execute(roomId);
            deleteRoomModel.statusDTO = statusDTO;
        } catch (Exception e) {
            statusDTO.setStatus(vn.giadinh.phonghoc.shared.enums.StatusCode.FAILURE);
            statusDTO.setMessage(e.getMessage());
            deleteRoomModel.statusDTO = statusDTO;
        }
        deleteRoomModel.notifySubscribers();
        return statusDTO;
    }
}
