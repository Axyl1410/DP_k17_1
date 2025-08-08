package vn.giadinh.phonghoc.presentation.controller;

import vn.giadinh.phonghoc.business.usecase.LoginUsecase;
import vn.giadinh.phonghoc.dto.AuthResponseDTO;
import vn.giadinh.phonghoc.dto.LoginRequestDTO;
import vn.giadinh.phonghoc.presentation.model.LoginModel;

public class LoginController {
    public static LoginModel loginModel = new LoginModel();

    public static AuthResponseDTO execute(LoginRequestDTO loginRequest) {
        try {
            AuthResponseDTO response = LoginUsecase.execute(loginRequest);
            loginModel.authResponseDTO = response;
            loginModel.notifySubscribers();
            return response;
        } catch (Exception e) {
            AuthResponseDTO errorResponse = new AuthResponseDTO(false, "Lỗi: " + e.getMessage(), null, null);
            loginModel.authResponseDTO = errorResponse;
            loginModel.notifySubscribers();
            return errorResponse;
        }
    }
}
