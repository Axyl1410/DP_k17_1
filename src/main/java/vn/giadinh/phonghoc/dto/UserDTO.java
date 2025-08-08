package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String passwordHash;
    private String fullName;
    private String role;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
}
