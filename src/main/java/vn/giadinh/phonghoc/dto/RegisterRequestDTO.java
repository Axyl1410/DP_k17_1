package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;
}
