package vn.giadinh.phonghoc.presentation.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import vn.giadinh.phonghoc.dto.ListViewDTO;
import vn.giadinh.phonghoc.presentation.controller.ViewRoomController;
import vn.giadinh.phonghoc.presentation.model.ViewRoomModel;
import vn.giadinh.phonghoc.presentation.observer.Subscriber;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class ListRoomView implements Initializable, Subscriber {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    // --- Toast ---
    public Label toast;
    public Button logoutButton;
    // --- Filter Components ---
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> buildingFilter; // Renamed from cityFilter
    @FXML
    private ComboBox<String> statusFilter;
    @FXML
    private ComboBox<String> lightFilter; // Renamed from genderFilter
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;
    // --- Form Components ---
    @FXML
    private TextField idField;
    @FXML
    private TextField buildingBlockField;
    @FXML
    private TextField areaField;
    @FXML
    private TextField numLightBulbsField;
    @FXML
    private DatePicker startDatePicker; // Changed from TextField to DatePicker
    @FXML
    private ComboBox<String> lightField; // Renamed from genderField
    @FXML
    private ComboBox<String> statusField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button clearButton;
    // --- Table Components ---
    @FXML
    private TableView<ListViewDTO> roomTable;
    @FXML
    private TableColumn<ListViewDTO, String> roomId;
    @FXML
    private TableColumn<ListViewDTO, String> buildingBlock;
    @FXML
    private TableColumn<ListViewDTO, String> area;
    @FXML
    private TableColumn<ListViewDTO, String> numLightBulbs;
    @FXML
    private TableColumn<ListViewDTO, String> startDateOfOperation;
    @FXML
    private TableColumn<ListViewDTO, String> sufficientLight;
    @FXML
    private TableColumn<ListViewDTO, String> isStandard;
    @FXML
    private TableColumn<ListViewDTO, String> actionColumn;
    private ViewRoomModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        setupEventHandlers();
        // Connect to the model
        ViewRoomModel model = ViewRoomController.getViewRoomModel();
        setViewModel(model);
        // Load initial data
        try {
            ViewRoomController.execute();
            toast.setText("Load data thành công" + viewModel.listViewDTOS.size() + "item");
        } catch (Exception e) {
            toast.setText(e.getMessage());
            e.printStackTrace();
            // Optionally show an error alert to the user
        }
    }

    private void setupTableColumns() {
        roomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        buildingBlock.setCellValueFactory(new PropertyValueFactory<>("buildingBlock"));
        area.setCellValueFactory(new PropertyValueFactory<>("area"));
        numLightBulbs.setCellValueFactory(new PropertyValueFactory<>("numLightBulbs"));
        startDateOfOperation.setCellValueFactory(new PropertyValueFactory<>("startDateOfOperation"));
        sufficientLight.setCellValueFactory(new PropertyValueFactory<>("sufficientLight"));
        isStandard.setCellValueFactory(new PropertyValueFactory<>("isStandard"));
        // Setup action column with edit/delete buttons
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Sửa");
            private final Button deleteButton = new Button("Xóa");
            private final HBox buttonBox = new HBox(5, editButton, deleteButton);

            {
                // Styling buttons for better look
                editButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                buttonBox.setAlignment(javafx.geometry.Pos.CENTER);
                editButton.setOnAction(event -> {
                    ListViewDTO item = getTableView().getItems().get(getIndex());
                    editRoom(item);
                });
                deleteButton.setOnAction(event -> {
                    ListViewDTO item = getTableView().getItems().get(getIndex());
                    deleteRoom(item);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                }
            }
        });
    }

    private void setupEventHandlers() {
        searchButton.setOnAction(event -> searchRooms());
        refreshButton.setOnAction(event -> refreshRooms());
        addButton.setOnAction(event -> addRoom());
        updateButton.setOnAction(event -> updateRoom());
        clearButton.setOnAction(event -> clearForm());
        // Table selection listener
        roomTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        loadRoomToForm(newValue);
                    }
                });
    }

    public void setViewModel(ViewRoomModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addSubscriber(this);
    }

    private void showList(ViewRoomModel roomViewModel) {
        roomTable.getItems().clear();
        if (roomViewModel.listViewDTOS != null) {
            roomTable.getItems().setAll(roomViewModel.listViewDTOS);
        }
    }

    private void searchRooms() {
        // Implementation for search functionality
        System.out.println("Searching rooms...");
    }

    private void refreshRooms() {
        // Implementation for refresh functionality
        System.out.println("Refreshing rooms...");
        try {
            clearForm();
            ViewRoomController.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addRoom() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vn/giadinh/phonghoc/add-room-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Thêm phòng học mới");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể mở cửa sổ thêm phòng");
            alert.setContentText("Đã xảy ra lỗi: " + e.getMessage());
            alert.showAndWait();
        }

    }

    private void updateRoom() {
        // Implementation for update room functionality
        System.out.println("Updating room...");
    }

    private void clearForm() {
        idField.clear();
        buildingBlockField.clear();
        areaField.clear();
        numLightBulbsField.clear();
        startDatePicker.setValue(null); // Clear DatePicker
        lightField.setValue(null); // Use updated name
        statusField.setValue(null);
    }

    private void editRoom(ListViewDTO room) {
        loadRoomToForm(room);
    }

    private void deleteRoom(ListViewDTO room) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa phòng này?");
        alert.setContentText("Phòng: " + room.getRoomId());
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Implementation for delete functionality
                System.out.println("Deleting room: " + room.getRoomId());
            }
        });
    }

    private void loadRoomToForm(ListViewDTO room) {
        idField.setText(room.getRoomId());
        buildingBlockField.setText(room.getBuildingBlock());
        areaField.setText(room.getArea());
        numLightBulbsField.setText(room.getNumLightBulbs());
        lightField.setValue(room.getSufficientLight());
        statusField.setValue(room.getIsStandard());
        // Safely parse the date string into the DatePicker
        try {
            if (room.getStartDateOfOperation() != null && !room.getStartDateOfOperation().isEmpty()) {
                LocalDate date = LocalDate.parse(room.getStartDateOfOperation(), dateFormatter);
                startDatePicker.setValue(date);
            } else {
                startDatePicker.setValue(null);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Could not parse date: " + room.getStartDateOfOperation());
            startDatePicker.setValue(null); // Set to null if parsing fails
        }
    }

    @Override
    public void update() {
        // IMPORTANT: Ensure UI updates run on the JavaFX Application Thread.
        Platform.runLater(() -> {
            if (viewModel != null && viewModel.listViewDTOS != null) {
                toast.setText("Updating UI with " + viewModel.listViewDTOS.size() + " items.");
                showList(viewModel);
                toast.setText("Đã cập nhật thông tin");
            } else {
                toast.setText("Update called, but viewmodel or list is null.");
            }
        });
    }
}