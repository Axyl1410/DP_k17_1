package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoomDTO {
    private String roomId;
    private String roomType;
    private String buildingBlock;
    private String area;
    private String numLightBulbs;
    private String startDateOfOperation;
    private String hasProjector;
    private String numComputers;
    private String specialization;
    private String capacity;
    private String hasSink;
}
