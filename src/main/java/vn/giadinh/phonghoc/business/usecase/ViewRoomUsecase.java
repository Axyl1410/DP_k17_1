package vn.giadinh.phonghoc.business.usecase;

import vn.giadinh.phonghoc.business.factory.RoomFactory;
import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.ViewRoomDTO;
import vn.giadinh.phonghoc.model.Room;
import vn.giadinh.phonghoc.persistence.dao.ViewRoomDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRoomUsecase {
    private static ViewRoomDAO viewRoomDAO;

    public static List<ViewRoomDTO> execute() throws SQLException {
        List<RoomDTO> roomDTOList = null;
        List<Room> roomList = null;
        roomDTOList = viewRoomDAO.getAll();
        roomList = convertToBusinessObjects(roomDTOList);
        return convertToViewRoomDTO(roomList);
    }

    private static List<Room> convertToBusinessObjects(List<RoomDTO> roomDTOList) {
        List<Room> rooms = new ArrayList<>();
        for (RoomDTO roomDTO : roomDTOList) {
            Room room = RoomFactory.createRoom(roomDTO);
            rooms.add(room);
        }
        return rooms;
    }

    private static List<ViewRoomDTO> convertToViewRoomDTO(List<Room> rooms) {
        List<ViewRoomDTO> viewRoomDTOList = new ArrayList<>();
        for (Room room : rooms) {
            ViewRoomDTO viewRoomDTO = new ViewRoomDTO();
            viewRoomDTO.setRoomId(room.getRoomId());
            viewRoomDTO.setBuildingBlock(room.getBuildingBlock());
            viewRoomDTO.setArea(room.getArea());
            viewRoomDTO.setNumLightBulbs(room.getNumLightBulbs());
            viewRoomDTO.setStartDateOfOperation((Date) room.getStartDateOfOperation());
            viewRoomDTO.setSufficientLight(room.checkSufficientLight());
            viewRoomDTO.setStandard(room.meetsStandard());
            viewRoomDTOList.add(viewRoomDTO);
        }
        return viewRoomDTOList;
    }

}
