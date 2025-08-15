package vn.giadinh.phonghoc.business.usecase;

import vn.giadinh.phonghoc.business.factory.RoomFactory;
import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.ViewRoomDTO;
import vn.giadinh.phonghoc.entity.Room;
import vn.giadinh.phonghoc.persistence.dao.ViewRoomDAO;
import vn.giadinh.phonghoc.persistence.gateway.ViewRoomGateway;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRoomUsecase {
    private static final ViewRoomGateway GATEWAY;

    static {
        try {
            GATEWAY = new ViewRoomDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ViewRoomDTO> execute() throws SQLException {
        List<RoomDTO> roomDTOList = GATEWAY.getAll();
        List<Room> roomList = convertToBusinessObjects(roomDTOList);
        return convertToViewRoomDTO(roomList);
    }

    private static List<Room> convertToBusinessObjects(List<RoomDTO> roomDTOList) {
        List<Room> rooms = new ArrayList<>();
        for (RoomDTO roomDTO : roomDTOList) {
            Room room = RoomFactory.createRoom(roomDTO);
            if (room != null) {
                rooms.add(room);
            }
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
            if (room.getStartDateOfOperation() != null) {
                viewRoomDTO.setStartDateOfOperation(new Date(room.getStartDateOfOperation().getTime()));
            }
            viewRoomDTO.setSufficientLight(room.checkSufficientLight());
            viewRoomDTO.setStandard(room.meetsStandard());
            viewRoomDTO.setRoomType(room.getRoomType());
            if (!viewRoomDTO.isStandard() || !viewRoomDTO.isSufficientLight())
                viewRoomDTOList.add(viewRoomDTO);
        }
        return viewRoomDTOList;
    }
}
