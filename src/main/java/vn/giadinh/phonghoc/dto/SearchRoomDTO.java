package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRoomDTO {
    private String searchTerm;
    private String buildingFilter;
    private String statusFilter;
    private String lightFilter;
}
