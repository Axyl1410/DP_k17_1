package vn.giadinh.phonghoc.presentation.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import vn.giadinh.phonghoc.dto.ListViewDTO;
import vn.giadinh.phonghoc.dto.RoomDetailDTO;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.dto.UpdateRoomDTO;
import vn.giadinh.phonghoc.presentation.controller.DeleteRoomController;
import vn.giadinh.phonghoc.presentation.controller.EditRoomController;
import vn.giadinh.phonghoc.presentation.controller.ViewRoomController;
import vn.giadinh.phonghoc.presentation.model.DeleteRoomModel;
import vn.giadinh.phonghoc.presentation.model.ViewRoomModel;
import vn.giadinh.phonghoc.presentation.observer.Subscriber;
import vn.giadinh.phonghoc.shared.common.FormNavigator;
import vn.giadinh.phonghoc.shared.enums.StatusCode;

import java.net.URL;
import java.util.ResourceBundle;

public class ListRoomView implements Initializable, Subscriber {
    // --- Toast ---
    @FXML
    public Label toast;
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
    private TextField startDatePicker; // Changed from TextField to DatePicker
    @FXML
    private TextField lightField; // Renamed from genderField
    @FXML
    private TextField statusField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button clearButton;
    // --- Logout Button ---
    @FXML
    private Button logoutButton;
    // --- Table Components ---
    @FXML
    private TableView<ListViewDTO> roomTable;
    @FXML
    private TableColumn<ListViewDTO, String> roomId;
    @FXML
    private TableColumn<ListViewDTO, String> buildingBlock;
    @FXML
    private TableColumn<ListViewDTO, String> roomType;
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
    @FXML
    private ViewRoomModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        setupEventHandlers();
        // Connect to the model
        ViewRoomModel model = ViewRoomController.viewRoomModel;
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
        roomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
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
        logoutButton.setOnAction(event -> handleLogout());
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
        toast.setText("Searching rooms...");
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
        FormNavigator.navigateToForm(toast.getScene(), "/vn/giadinh/phonghoc/add-room-view.fxml", "Thêm phòng học mới");
    }

    private void updateRoom() {
        try {
            // Get data from form fields
            UpdateRoomDTO updateRoomDTO = new UpdateRoomDTO();
            updateRoomDTO.setRoomId(idField.getText());
            updateRoomDTO.setBuildingBlock(buildingBlockField.getText());
            try {
                updateRoomDTO.setArea(Double.parseDouble(areaField.getText()));
            } catch (NumberFormatException e) {
                toast.setText("Diện tích phải là số");
                return;
            }
            try {
                updateRoomDTO.setNumLightBulbs(Integer.parseInt(numLightBulbsField.getText()));
            } catch (NumberFormatException e) {
                toast.setText("Số bóng đèn phải là số nguyên");
                return;
            }
            // Set other fields as needed
            updateRoomDTO.setRoomType("LECTURE_HALL"); // Default type, can be enhanced with dropdown
            updateRoomDTO.setHasProjector(false);
            updateRoomDTO.setNumComputers(0);
            updateRoomDTO.setCapacity(0);
            updateRoomDTO.setHasSink(false);
            // Execute update
            EditRoomController.execute(updateRoomDTO);
            toast.setText("Cập nhật phòng học thành công");
            // Refresh the list
            refreshRooms();
        } catch (Exception e) {
            toast.setText("Lỗi cập nhật: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearForm() {
        idField.clear();
        buildingBlockField.clear();
        areaField.clear();
        numLightBulbsField.clear();
        startDatePicker.clear(); // Clear DatePicker
        lightField.clear(); // Use updated name
        statusField.clear();
    }

    private void editRoom(ListViewDTO room) {
        // Navigate to edit form and get the controller
        EditRoomView editRoomView = FormNavigator.navigateToFormWithData(
                toast.getScene(),
                "/vn/giadinh/phonghoc/edit-room-view.fxml",
                "Chỉnh sửa phòng học");
        // Set the room data to edit
        if (editRoomView != null) {
            editRoomView.setRoomIdToEdit(room.getRoomId());
            System.out.println("Navigating to edit form for room: " + room.getRoomId());
        }
    }

    private void deleteRoom(ListViewDTO room) {
        // Tạo RoomDetailDTO để hiển thị thông tin chi tiết
        RoomDetailDTO roomDetail = new RoomDetailDTO(room);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa phòng học");
        alert.setHeaderText("Xác nhận xóa phòng học");
        alert.setContentText(roomDetail.getDisplayText());
        // Thiết lập kích thước alert để hiển thị đầy đủ thông tin
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(500);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    DeleteRoomModel deleteRoomModel = DeleteRoomController.deleteRoomModel;
                    DeleteRoomController.execute(room.getRoomId());
                    StatusDTO modelStatusDTO = deleteRoomModel.statusDTO;
                    StatusCode status = modelStatusDTO.getStatus();
                    String message = modelStatusDTO.getMessage();
                    System.out.println("Status: " + status + ", Message: " + message); // Debug info
                    if (status.equals(StatusCode.SUCCESS)) {
                        showAlert("Thành công", "Phòng học đã được xóa thành công!");
                        toast.setText(message);
                        refreshRooms();
                    } else {
                        toast.setText("Lỗi: " + message);
                        showAlert("Lỗi", "Không thể xóa phòng học: " + message);
                    }
                } catch (Exception e) {
                    toast.setText("Lỗi xóa phòng: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadRoomToForm(ListViewDTO room) {
        idField.setText(room.getRoomId());
        buildingBlockField.setText(room.getBuildingBlock());
        areaField.setText(room.getArea());
        numLightBulbsField.setText(room.getNumLightBulbs());
        lightField.setText(room.getSufficientLight());
        statusField.setText(room.getIsStandard());
        startDatePicker.setText(room.getStartDateOfOperation());
    }

    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText("Xác nhận đăng xuất");
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Navigate back to login form
                    FormNavigator.navigateToForm(toast.getScene(), "/vn/giadinh/phonghoc/login-view.fxml", "Đăng Nhập");
                    toast.setText("Đã đăng xuất thành công");
                } catch (Exception e) {
                    toast.setText("Lỗi đăng xuất: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void update() {
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