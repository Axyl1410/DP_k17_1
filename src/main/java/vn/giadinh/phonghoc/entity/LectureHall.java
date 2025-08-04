package vn.giadinh.phonghoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureHall extends Room {
    public boolean hasProjector;

    @Override
    public boolean meetsStandard() {
        return hasProjector;
    }
}
