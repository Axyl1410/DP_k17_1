package vn.giadinh.phonghoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComputerLab extends Room {
    public int numComputers;

    @Override
    public boolean meetsStandard() {
        return numComputers >= area / 1.5 && checkSufficientLight();
    }
}
