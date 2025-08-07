package vn.giadinh.phonghoc.presentation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.presentation.observer.Publisher;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoomModel extends Publisher {
    public StatusDTO statusDTO;
}
