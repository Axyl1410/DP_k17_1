package vn.giadinh.phonghoc.presentation.controller;

import vn.giadinh.phonghoc.business.usecase.ViewRoomUsecase;
import vn.giadinh.phonghoc.dto.ListViewDTO;
import vn.giadinh.phonghoc.dto.ViewRoomDTO;
import vn.giadinh.phonghoc.presentation.model.ViewRoomModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRoomController {
    private static ViewRoomModel viewRoomModel;

    public static void execute() throws SQLException {
        List<ViewRoomDTO> viewRoomDTOS = ViewRoomUsecase.execute();

        // Initialize viewRoomModel if it's null
        if (viewRoomModel == null) {
            viewRoomModel = new ViewRoomModel();
        }

        viewRoomModel.listViewDTOS = convertToViewObject(viewRoomDTOS);

        System.out.println("=== ViewRoomController - ListViewDTO List ===");
        for (ListViewDTO dto : viewRoomModel.listViewDTOS) {
            System.out.println("ListViewDTO: " + dto.getRoomId() + " - " + dto.getBuildingBlock() + " - "
                    + dto.getArea() + " - " + dto.getIsStandard());
        }

        viewRoomModel.notifySubscribers();
    }

    public static ViewRoomModel getViewRoomModel() {
        if (viewRoomModel == null) {
            viewRoomModel = new ViewRoomModel();
        }
        return viewRoomModel;
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

    // Main method để test
    public static void main(String[] args) {
        System.out.println("=== Testing ViewRoomController ===");
        try {
            execute();
            System.out.println("=== ViewRoomController Test Completed ===");
            System.out.println("Total ListViewDTO: " + (viewRoomModel != null ? viewRoomModel.listViewDTOS.size() : 0));
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
