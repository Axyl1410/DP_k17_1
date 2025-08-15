package vn.giadinh.phonghoc.presentation.model;

import lombok.Getter;
import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.presentation.observer.Publisher;

@Getter
public class AddRoomModel extends Publisher {
    private RoomDTO roomData;
    private StatusDTO status;
    private boolean isFormValid;

    public AddRoomModel() {
        this.roomData = new RoomDTO();
        this.status = new StatusDTO();
        this.isFormValid = false;
    }

    public void setRoomData(RoomDTO roomData) {
        this.roomData = roomData;
        notifySubscribers();
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
        notifySubscribers();
    }

    public void setFormValid(boolean formValid) {
        this.isFormValid = formValid;
        notifySubscribers();
    }

    public void clearData() {
        this.roomData = new RoomDTO();
        this.status = new StatusDTO();
        this.isFormValid = false;
        notifySubscribers();
    }
}
