package vn.giadinh.phonghoc.presentation.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import vn.giadinh.phonghoc.dto.AuthResponseDTO;
import vn.giadinh.phonghoc.dto.RegisterRequestDTO;
import vn.giadinh.phonghoc.presentation.controller.RegisterController;
import vn.giadinh.phonghoc.presentation.model.RegisterModel;
import vn.giadinh.phonghoc.presentation.observer.Subscriber;
import vn.giadinh.phonghoc.shared.common.FormNavigator;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterView implements Initializable, Subscriber {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private CheckBox termsCheckBox;

    @FXML
    private Button registerButton;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private Label statusLabel;

    private RegisterModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupEventHandlers();
        this.model = new RegisterModel();
        setViewModel(model);
    }

    public void setViewModel(RegisterModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    private void setupEventHandlers() {
        // Register button handler
        registerButton.setOnAction(event -> handleRegister());

        // Login link handler
        loginLink.setOnAction(event -> navigateToLogin());

        // Enter key handlers
        fullNameField.setOnAction(event -> handleRegister());
        usernameField.setOnAction(event -> handleRegister());
        emailField.setOnAction(event -> handleRegister());
        passwordField.setOnAction(event -> handleRegister());
        confirmPasswordField.setOnAction(event -> handleRegister());
    }

    private void handleRegister() {
        try {
            // Validate terms checkbox
            if (!termsCheckBox.isSelected()) {
                showStatusMessage("Vui lòng đồng ý với điều khoản sử dụng", false);
                return;
            }

            // Get input values
            String fullName = fullNameField.getText().trim();
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Create register request
            RegisterRequestDTO registerRequest = new RegisterRequestDTO(username, email, password, confirmPassword,
                    fullName);

            // Execute register
            AuthResponseDTO response = RegisterController.execute(registerRequest);

            // Handle response
            if (response.getSuccess()) {
                showAlert("Thành công", "Đăng ký thành công! Vui lòng đăng nhập.");
                // Navigate to login
                navigateToLogin();
            } else {
                showStatusMessage(response.getMessage(), false);
            }

        } catch (Exception e) {
            showStatusMessage("Lỗi: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    private void navigateToLogin() {
        FormNavigator.navigateToForm(registerButton.getScene(), "/vn/giadinh/phonghoc/login-view.fxml", "Đăng Nhập");
    }

    private void showStatusMessage(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setVisible(true);
        statusLabel.setStyle(isSuccess ? "-fx-text-fill: #27ae60;" : "-fx-text-fill: #e74c3c;");

        // Hide message after 3 seconds
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> statusLabel.setVisible(false));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void update() {
        // Handle updates from model if needed
        if (model.authResponseDTO != null) {
            AuthResponseDTO response = model.authResponseDTO;
            if (response.getSuccess()) {
                showStatusMessage(response.getMessage(), true);
                navigateToLogin();
            } else {
                showStatusMessage(response.getMessage(), false);
            }
        }
    }
}
