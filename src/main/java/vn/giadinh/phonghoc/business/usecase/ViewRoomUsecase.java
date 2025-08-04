package vn.giadinh.phonghoc.business.usecase;

import vn.giadinh.phonghoc.business.factory.RoomFactory;
import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.ViewRoomDTO;
import vn.giadinh.phonghoc.entity.Room;
import vn.giadinh.phonghoc.persistence.dao.ViewRoomDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRoomUsecase {
    private static ViewRoomDAO viewRoomDAO;

    public static List<ViewRoomDTO> execute() throws SQLException {
        // Initialize viewRoomDAO if it's null
        if (viewRoomDAO == null) {
            try {
                viewRoomDAO = new ViewRoomDAO();
            } catch (SQLException e) {
                throw new SQLException("Không thể kết nối database: " + e.getMessage());
            }
        }

        List<RoomDTO> roomDTOList = viewRoomDAO.getAll();
        System.out.println("=== ViewRoomUsecase - RoomDTO List ===");
        for (RoomDTO dto : roomDTOList) {
            System.out.println(
                    "RoomDTO: " + dto.getRoomId() + " - " + dto.getRoomType() + " - " + dto.getBuildingBlock());
        }

        List<Room> roomList = convertToBusinessObjects(roomDTOList);
        System.out.println("=== ViewRoomUsecase - Room List ===");
        for (Room room : roomList) {
            System.out.println("Room: " + room.getRoomId() + " - " + room.getClass().getSimpleName());
        }

        return convertToViewRoomDTO(roomList);
    }

    private static List<Room> convertToBusinessObjects(List<RoomDTO> roomDTOList) {
        List<Room> rooms = new ArrayList<>();
        for (RoomDTO roomDTO : roomDTOList) {
            Room room = RoomFactory.createRoom(roomDTO);
            if (room != null) {
                System.out.println(room);
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

            // Handle null date
            if (room.getStartDateOfOperation() != null) {
                viewRoomDTO.setStartDateOfOperation(new Date(room.getStartDateOfOperation().getTime()));
            }

            viewRoomDTO.setSufficientLight(room.checkSufficientLight());
            viewRoomDTO.setStandard(room.meetsStandard());
            viewRoomDTOList.add(viewRoomDTO);
        }

        System.out.println("=== ViewRoomUsecase - ViewRoomDTO List ===");
        for (ViewRoomDTO dto : viewRoomDTOList) {
            System.out.println(
                    "ViewRoomDTO: " + dto.getRoomId() + " - " + dto.getBuildingBlock() + " - " + dto.isStandard());
        }

        return viewRoomDTOList;
    }

    // Main method để test
    public static void main(String[] args) {
        System.out.println("=== Testing ViewRoomUsecase ===");
        try {
            List<ViewRoomDTO> result = execute();
            System.out.println("=== Final Result ===");
            System.out.println("Total rooms: " + result.size());
            for (ViewRoomDTO dto : result) {
                System.out.println("Final: " + dto.getRoomId() + " - " + dto.getBuildingBlock() + " - " + dto.getArea()
                        + " - " + dto.isStandard());
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
