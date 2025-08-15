package vn.giadinh.phonghoc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vn.giadinh.phonghoc.persistence.dao.AddRoomDAO;
import vn.giadinh.phonghoc.persistence.gateway.AddRoomGateway;
import vn.giadinh.phonghoc.presentation.controller.AddRoomController;
import vn.giadinh.phonghoc.presentation.model.AddRoomModel;
import vn.giadinh.phonghoc.presentation.view.AddRoomView;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        // Initialize MVC components
        AddRoomModel model = new AddRoomModel();
        AddRoomGateway gateway = new AddRoomDAO();
        AddRoomController controller = new AddRoomController(model, gateway);
        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vn/giadinh/phonghoc/add-room-view.fxml"));
        Parent root = loader.load();
        // Get the view and set up MVC connections
        AddRoomView view = loader.getController();
        view.setModel(model);
        view.setController(controller);
        // Set up the stage
        primaryStage.setTitle("Thêm Phòng Học - Hệ Thống Quản Lý Phòng Học");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}