package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    public String roomId;
    public String roomType;
    public String buildingBlock;
    public Double area;
    public Integer numLightBulbs;
    public Date startDateOfOperation;
    public Boolean hasProjector;
    public Integer numComputers;
    public String specialization;
    public Integer capacity;
    public Boolean hasSink;
}
