package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRoomDTO {
    private String roomId;
    private String buildingBlock;
    private double area;
    private int numLightBulbs;
    private Date startDateOfOperation;
    private boolean sufficientLight;
    private boolean isStandard;
}
