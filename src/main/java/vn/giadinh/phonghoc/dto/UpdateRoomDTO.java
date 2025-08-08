package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomDTO {
    private String roomId;
    private String buildingBlock;
    private Double area;
    private Integer numLightBulbs;
    private Date startDateOfOperation;
    private String roomType;
    private Boolean hasProjector;
    private Integer numComputers;
    private String specialization;
    private Integer capacity;
    private Boolean hasSink;
}
