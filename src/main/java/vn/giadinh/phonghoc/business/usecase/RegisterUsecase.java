package vn.giadinh.phonghoc.business.usecase;

import vn.giadinh.phonghoc.dto.AuthResponseDTO;
import vn.giadinh.phonghoc.dto.RegisterRequestDTO;
import vn.giadinh.phonghoc.dto.UserDTO;
import vn.giadinh.phonghoc.persistence.dao.AuthDAO;
import vn.giadinh.phonghoc.persistence.gateway.AuthGateway;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegisterUsecase {
    private static final AuthGateway GATEWAY;

    static {
        try {
            GATEWAY = new AuthDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static AuthResponseDTO execute(RegisterRequestDTO registerRequest) {
        try {
            // Validate input
            if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty()) {
                return new AuthResponseDTO(false, "Tên đăng nhập không được để trống", null, null);
            }

            if (registerRequest.getEmail() == null || registerRequest.getEmail().trim().isEmpty()) {
                return new AuthResponseDTO(false, "Email không được để trống", null, null);
            }

            if (registerRequest.getPassword() == null || registerRequest.getPassword().trim().isEmpty()) {
                return new AuthResponseDTO(false, "Mật khẩu không được để trống", null, null);
            }

            if (registerRequest.getConfirmPassword() == null || registerRequest.getConfirmPassword().trim().isEmpty()) {
                return new AuthResponseDTO(false, "Xác nhận mật khẩu không được để trống", null, null);
            }

            if (registerRequest.getFullName() == null || registerRequest.getFullName().trim().isEmpty()) {
                return new AuthResponseDTO(false, "Họ tên không được để trống", null, null);
            }

            // Validate password match
            if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                return new AuthResponseDTO(false, "Mật khẩu và xác nhận mật khẩu không khớp", null, null);
            }

            // Validate password length
            if (registerRequest.getPassword().length() < 6) {
                return new AuthResponseDTO(false, "Mật khẩu phải có ít nhất 6 ký tự", null, null);
            }

            // Validate email format
            if (!isValidEmail(registerRequest.getEmail())) {
                return new AuthResponseDTO(false, "Email không hợp lệ", null, null);
            }

            // Check if username exists
            if (GATEWAY.isUsernameExists(registerRequest.getUsername())) {
                return new AuthResponseDTO(false, "Tên đăng nhập đã tồn tại", null, null);
            }

            // Check if email exists
            if (GATEWAY.isEmailExists(registerRequest.getEmail())) {
                return new AuthResponseDTO(false, "Email đã tồn tại", null, null);
            }

            // Create user DTO
            UserDTO user = new UserDTO();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPasswordHash(registerRequest.getPassword()); // Will be hashed in DAO
            user.setFullName(registerRequest.getFullName());
            user.setRole("USER");

            // Register user
            UserDTO registeredUser = GATEWAY.registerUser(user);

            if (registeredUser != null) {
                return new AuthResponseDTO(true, "Đăng ký thành công", registeredUser, null);
            } else {
                return new AuthResponseDTO(false, "Không thể đăng ký người dùng", null, null);
            }

        } catch (SQLException e) {
            return new AuthResponseDTO(false, "Lỗi cơ sở dữ liệu: " + e.getMessage(), null, null);
        } catch (Exception e) {
            return new AuthResponseDTO(false, "Lỗi không xác định: " + e.getMessage(), null, null);
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
