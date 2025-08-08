package vn.giadinh.phonghoc.presentation.controller;

import vn.giadinh.phonghoc.business.usecase.RegisterUsecase;
import vn.giadinh.phonghoc.dto.AuthResponseDTO;
import vn.giadinh.phonghoc.dto.RegisterRequestDTO;
import vn.giadinh.phonghoc.presentation.model.RegisterModel;

public class RegisterController {
    public static RegisterModel registerModel = new RegisterModel();

    public static AuthResponseDTO execute(RegisterRequestDTO registerRequest) {
        try {
            AuthResponseDTO response = RegisterUsecase.execute(registerRequest);
            registerModel.authResponseDTO = response;
            registerModel.notifySubscribers();
            return response;
        } catch (Exception e) {
            AuthResponseDTO errorResponse = new AuthResponseDTO(false, "Lỗi: " + e.getMessage(), null, null);
            registerModel.authResponseDTO = errorResponse;
            registerModel.notifySubscribers();
            return errorResponse;
        }
    }
}
