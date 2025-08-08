package vn.giadinh.phonghoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Room {
    protected String roomId;
    protected String buildingBlock;
    protected double area;
    protected int numLightBulbs;
    protected Date startDateOfOperation;
    protected String roomType;

    public Boolean checkSufficientLight() {
        return numLightBulbs >= area / 10;
    }

    public abstract boolean meetsStandard();
}
