package vn.giadinh.phonghoc.business.factory;

import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.entity.ComputerLab;
import vn.giadinh.phonghoc.entity.Laboratory;
import vn.giadinh.phonghoc.entity.LectureHall;
import vn.giadinh.phonghoc.entity.Room;

public class RoomFactory {
    public static Room createRoom(RoomDTO roomDTO) {
        if ("LABORATORY".equalsIgnoreCase(roomDTO.getRoomType())) {
            return getLaboratory(roomDTO);
        } else if ("COMPUTER_LAB".equalsIgnoreCase(roomDTO.getRoomType())) {
            return getComputerLab(roomDTO);
        } else if ("LECTURE_HALL".equalsIgnoreCase(roomDTO.getRoomType())) {
            return getLectureHall(roomDTO);
        }
        return null;
    }

    private static Laboratory getLaboratory(RoomDTO roomDTO) {
        Laboratory laboratory = new Laboratory();
        laboratory.setRoomId(roomDTO.getRoomId());
        laboratory.setBuildingBlock(roomDTO.getBuildingBlock());
        laboratory.setArea(roomDTO.getArea());
        laboratory.setNumLightBulbs(roomDTO.getNumLightBulbs());
        laboratory.setStartDateOfOperation(roomDTO.getStartDateOfOperation());
        laboratory.setSpecialization(roomDTO.getSpecialization());
        laboratory.setCapacity(roomDTO.getCapacity());
        laboratory.setHasSink(roomDTO.getHasSink() != null ? roomDTO.getHasSink() : false);
        return laboratory;
    }

    private static LectureHall getLectureHall(RoomDTO roomDTO) {
        LectureHall lectureHall = new LectureHall();
        lectureHall.setRoomId(roomDTO.getRoomId());
        lectureHall.setBuildingBlock(roomDTO.getBuildingBlock());
        lectureHall.setArea(roomDTO.getArea());
        lectureHall.setNumLightBulbs(roomDTO.getNumLightBulbs());
        lectureHall.setStartDateOfOperation(roomDTO.getStartDateOfOperation());
        lectureHall.setHasProjector(roomDTO.getHasProjector() != null ? roomDTO.getHasProjector() : false);
        return lectureHall;
    }

    private static ComputerLab getComputerLab(RoomDTO roomDTO) {
        ComputerLab computerLab = new ComputerLab();
        computerLab.setRoomId(roomDTO.getRoomId());
        computerLab.setBuildingBlock(roomDTO.getBuildingBlock());
        computerLab.setArea(roomDTO.getArea());
        computerLab.setNumLightBulbs(roomDTO.getNumLightBulbs());
        computerLab.setStartDateOfOperation(roomDTO.getStartDateOfOperation());
        computerLab.setNumComputers(roomDTO.getNumComputers());
        return computerLab;
    }
}
