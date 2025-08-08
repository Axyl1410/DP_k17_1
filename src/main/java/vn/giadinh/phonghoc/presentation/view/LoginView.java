package vn.giadinh.phonghoc.presentation.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import vn.giadinh.phonghoc.dto.AuthResponseDTO;
import vn.giadinh.phonghoc.dto.LoginRequestDTO;
import vn.giadinh.phonghoc.presentation.controller.LoginController;
import vn.giadinh.phonghoc.presentation.model.LoginModel;
import vn.giadinh.phonghoc.presentation.observer.Subscriber;
import vn.giadinh.phonghoc.shared.common.FormNavigator;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginView implements Initializable, Subscriber {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private Label statusLabel;

    private LoginModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupEventHandlers();
        this.model = new LoginModel();
        setViewModel(model);
    }

    public void setViewModel(LoginModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    private void setupEventHandlers() {
        // Login button handler
        loginButton.setOnAction(event -> handleLogin());

        // Register link handler
        registerLink.setOnAction(event -> navigateToRegister());

        // Forgot password link handler
        forgotPasswordLink.setOnAction(event -> handleForgotPassword());

        // Enter key handlers
        usernameField.setOnAction(event -> handleLogin());
        passwordField.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        try {
            // Get input values
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            // Create login request
            LoginRequestDTO loginRequest = new LoginRequestDTO(username, password);

            // Execute login
            AuthResponseDTO response = LoginController.execute(loginRequest);

            // Handle response
            if (response.getSuccess()) {
                showAlert("Thành công", "Đăng nhập thành công!");
                // Navigate to main application
                navigateToMainApp();
            } else {
                showStatusMessage(response.getMessage(), false);
            }

        } catch (Exception e) {
            showStatusMessage("Lỗi: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    private void navigateToRegister() {
        FormNavigator.navigateToForm(loginButton.getScene(), "/vn/giadinh/phonghoc/register-view.fxml", "Đăng Ký");
    }

    private void navigateToMainApp() {
        FormNavigator.navigateToForm(loginButton.getScene(), "/vn/giadinh/phonghoc/list-room-view.fxml",
                "Quản lý phòng học");
    }

    private void handleForgotPassword() {
        showAlert("Thông báo", "Tính năng quên mật khẩu sẽ được phát triển trong tương lai.");
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
                navigateToMainApp();
            } else {
                showStatusMessage(response.getMessage(), false);
            }
        }
    }
}
