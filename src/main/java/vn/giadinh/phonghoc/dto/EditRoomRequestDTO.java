package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditRoomRequestDTO {
    private String roomId;
    private String buildingBlock;
    private String area;
    private String numLightBulbs;
    private String startDateOfOperation;
    private String sufficientLight;
    private String isStandard;

    // Constructor từ ListViewDTO
    public EditRoomRequestDTO(ListViewDTO listViewDTO) {
        this.roomId = listViewDTO.getRoomId();
        this.buildingBlock = listViewDTO.getBuildingBlock();
        this.area = listViewDTO.getArea();
        this.numLightBulbs = listViewDTO.getNumLightBulbs();
        this.startDateOfOperation = listViewDTO.getStartDateOfOperation();
        this.sufficientLight = listViewDTO.getSufficientLight();
        this.isStandard = listViewDTO.getIsStandard();
    }
}
