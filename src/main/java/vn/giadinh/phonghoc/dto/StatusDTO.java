package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.giadinh.phonghoc.shared.enums.StatusCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    private StatusCode status;
    private String message;

}
