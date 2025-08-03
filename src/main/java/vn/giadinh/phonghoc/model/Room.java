package vn.giadinh.phonghoc.model;

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

    public Boolean checkSufficientLight() {
        return numLightBulbs >= area / 10;
    }

    public abstract boolean meetsStandard();
}
