package vn.giadinh.phonghoc.presentation.controller;

import lombok.Getter;
import vn.giadinh.phonghoc.business.usecase.ViewRoomUsecase;
import vn.giadinh.phonghoc.dto.ListViewDTO;
import vn.giadinh.phonghoc.dto.ViewRoomDTO;
import vn.giadinh.phonghoc.presentation.model.ViewRoomModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRoomController {
    @Getter
    private static final ViewRoomModel viewRoomModel = new ViewRoomModel();

    public static void execute() throws SQLException {
        List<ViewRoomDTO> viewRoomDTOS = ViewRoomUsecase.execute();
        viewRoomModel.listViewDTOS = convertToViewObject(viewRoomDTOS);
        viewRoomModel.notifySubscribers();
    }

    private static List<ListViewDTO> convertToViewObject(List<ViewRoomDTO> viewRoomDTOs) {
        List<ListViewDTO> listViewDTOS = new ArrayList<>();
        for (ViewRoomDTO dto : viewRoomDTOs) {
            ListViewDTO item = new ListViewDTO();
            item.setRoomId(dto.getRoomId());
            item.setBuildingBlock(dto.getBuildingBlock());
            item.setArea(String.valueOf(dto.getArea()));
            item.setNumLightBulbs(String.valueOf(dto.getNumLightBulbs()));
            item.setStartDateOfOperation(String.valueOf(dto.getStartDateOfOperation()));
            item.setSufficientLight(String.valueOf(dto.isSufficientLight()));
            item.setIsStandard(String.valueOf(dto.isStandard()));
            listViewDTOS.add(item);
        }
        return listViewDTOS;
    }

}
