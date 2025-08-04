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
    public double area;
    public int numLightBulbs;
    public Date startDateOfOperation;
    public Boolean hasProjector;
    public int numComputers;
    public String specialization;
    public int capacity;
    public Boolean hasSink;
}
