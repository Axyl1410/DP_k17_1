package vn.giadinh.phonghoc.shared.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class FormNavigator {
    public static void navigateToForm(Scene currentScene, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(FormNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();
            currentScene.setRoot(root);
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(title);

        } catch (IOException e) {
            e.printStackTrace();
            showError("Lỗi", "Không thể mở form: " + e.getMessage());
        }
    }

    public static <T> T navigateToFormWithData(Scene currentScene, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(FormNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();
            currentScene.setRoot(root);
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(title);

            // Return the controller for further configuration
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Lỗi", "Không thể mở form: " + e.getMessage());
            return null;
        }
    }

    public static void openFormInNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(FormNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Lỗi", "Không thể mở cửa sổ mới: " + e.getMessage());
        }
    }

    private static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title != null ? title : "Lỗi");
        alert.setHeaderText("Không thể mở form thêm phòng");
        alert.setContentText("Đã xảy ra lỗi: " + message);
        alert.showAndWait();
    }
}