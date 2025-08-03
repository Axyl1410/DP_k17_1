package vn.giadinh.phonghoc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laboratory extends Room {
    public String specialization;
    public int capacity;
    public boolean hasSink;

    @Override
    public boolean meetsStandard() {
        return hasSink;
    }
}
