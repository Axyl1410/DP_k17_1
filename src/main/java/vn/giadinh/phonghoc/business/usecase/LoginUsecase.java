package vn.giadinh.phonghoc.business.usecase;

import vn.giadinh.phonghoc.dto.AuthResponseDTO;
import vn.giadinh.phonghoc.dto.LoginRequestDTO;
import vn.giadinh.phonghoc.dto.UserDTO;
import vn.giadinh.phonghoc.persistence.dao.AuthDAO;
import vn.giadinh.phonghoc.persistence.gateway.AuthGateway;

import java.sql.SQLException;

public class LoginUsecase {
    private static final AuthGateway GATEWAY;

    static {
        try {
            GATEWAY = new AuthDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static AuthResponseDTO execute(LoginRequestDTO loginRequest) {
        try {
            // Validate input
            if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()) {
                return new AuthResponseDTO(false, "Tên đăng nhập không được để trống", null, null);
            }

            if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return new AuthResponseDTO(false, "Mật khẩu không được để trống", null, null);
            }

            // Authenticate user
            UserDTO user = GATEWAY.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

            if (user != null) {
                return new AuthResponseDTO(true, "Đăng nhập thành công", user, null);
            } else {
                return new AuthResponseDTO(false, "Tên đăng nhập hoặc mật khẩu không đúng", null, null);
            }

        } catch (SQLException e) {
            return new AuthResponseDTO(false, "Lỗi cơ sở dữ liệu: " + e.getMessage(), null, null);
        } catch (Exception e) {
            return new AuthResponseDTO(false, "Lỗi không xác định: " + e.getMessage(), null, null);
        }
    }
}
