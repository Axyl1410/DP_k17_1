package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListViewDTO {
    private String roomId;
    private String buildingBlock;
    private String area;
    private String numLightBulbs;
    private String startDateOfOperation;
    private String sufficientLight;
    private String isStandard;
}
