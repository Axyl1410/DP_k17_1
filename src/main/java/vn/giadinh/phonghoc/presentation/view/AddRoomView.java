package vn.giadinh.phonghoc.presentation.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import vn.giadinh.phonghoc.dto.RoomDTO;
import vn.giadinh.phonghoc.dto.StatusDTO;
import vn.giadinh.phonghoc.presentation.controller.AddRoomController;
import vn.giadinh.phonghoc.presentation.model.AddRoomModel;
import vn.giadinh.phonghoc.presentation.observer.Subscriber;
import vn.giadinh.phonghoc.shared.common.FormNavigator;
import vn.giadinh.phonghoc.shared.enums.RoomType;
import vn.giadinh.phonghoc.shared.enums.StatusCode;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddRoomView implements Initializable, Subscriber {
    // Specialization options for laboratory
    private static final List<String> SPECIALIZATIONS = Arrays.asList(
            "Chemistry", "Physics", "Biology", "Computer Science",
            "Environmental Science", "Mathematics", "Engineering");
    // Basic Information Fields
    @FXML
    private TextField roomIdField;
    @FXML
    private ComboBox<String> roomTypeComboBox;
    @FXML
    private TextField buildingBlockField;
    @FXML
    private TextField areaField;
    @FXML
    private TextField numLightBulbsField;
    @FXML
    private DatePicker startDatePicker;
    // Lecture Hall Fields
    @FXML
    private VBox lectureHallSection;
    @FXML
    private CheckBox hasProjectorCheckBox;
    // Computer Lab Fields
    @FXML
    private VBox computerLabSection;
    @FXML
    private TextField numComputersField;
    // Laboratory Fields
    @FXML
    private VBox laboratorySection;
    @FXML
    private ComboBox<String> specializationComboBox;
    @FXML
    private TextField capacityField;
    @FXML
    private CheckBox hasSinkCheckBox;
    // Placeholder section
    @FXML
    private VBox placeholderSection;
    // Action Buttons
    @FXML
    private Button saveButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button backButton;
    private AddRoomModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupRoomTypeComboBox();
        setupSpecializationComboBox();
        setupEventHandlers();
        hideAllSpecificSections();
        this.model = new AddRoomModel();
        setViewModel(model);
    }

    public void setViewModel(AddRoomModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    private void setupRoomTypeComboBox() {
        // Add room type options using enum
        for (RoomType type : RoomType.values()) {
            roomTypeComboBox.getItems().add(type.getFullDisplayName());
        }
        // Add listener for room type selection
        roomTypeComboBox.setOnAction(event -> {
            String selectedItem = roomTypeComboBox.getValue();
            if (selectedItem != null) {
                handleRoomTypeSelection(selectedItem);
            }
        });
    }

    private void setupSpecializationComboBox() {
        specializationComboBox.getItems().addAll(SPECIALIZATIONS);
    }

    private void setupEventHandlers() {
        // Save button handler
        saveButton.setOnAction(event -> handleSaveRoom());
        // Clear button handler
        clearButton.setOnAction(event -> clearForm());
        // Cancel button handler
        cancelButton.setOnAction(event -> handleBack());
        // Back button handler
        backButton.setOnAction(event -> handleBack());
    }

    private void handleRoomTypeSelection(String selectedItem) {
        // Hide all specific sections first
        hideAllSpecificSections();
        // Show appropriate section based on selection
        if (selectedItem.contains(RoomType.LECTURE_HALL.getCode())) {
            lectureHallSection.setVisible(true);
            lectureHallSection.setManaged(true);
            placeholderSection.setVisible(false);
            placeholderSection.setManaged(false);
        } else if (selectedItem.contains(RoomType.COMPUTER_LAB.getCode())) {
            computerLabSection.setVisible(true);
            computerLabSection.setManaged(true);
            placeholderSection.setVisible(false);
            placeholderSection.setManaged(false);
        } else if (selectedItem.contains(RoomType.LABORATORY.getCode())) {
            laboratorySection.setVisible(true);
            laboratorySection.setManaged(true);
            placeholderSection.setVisible(false);
            placeholderSection.setManaged(false);
        }
    }

    private void hideAllSpecificSections() {
        lectureHallSection.setVisible(false);
        lectureHallSection.setManaged(false);
        computerLabSection.setVisible(false);
        computerLabSection.setManaged(false);
        laboratorySection.setVisible(false);
        laboratorySection.setManaged(false);
        // Show placeholder when no section is selected
        placeholderSection.setVisible(true);
        placeholderSection.setManaged(true);
    }

    private void handleSaveRoom() {
        try {
            RoomDTO roomDTO = createRoomDTO();
            model.statusDTO = AddRoomController.execute(roomDTO);
            StatusDTO statusdto = model.statusDTO;
            StatusCode status = statusdto.getStatus();
            String message = statusdto.getMessage();
            System.out.println("Status: " + status + ", Message: " + message); // Debug info
            if (status.equals(StatusCode.SUCCESS)) {
                showAlert("Thành công", "Phòng học đã được lưu thành công!");
                clearForm();
            } else {
                showAlert("Lỗi", "Không thể lưu phòng học: " + message);
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi",
                    "Dữ liệu không hợp lệ: Vui lòng nhập đúng định dạng số cho diện tích, số bóng đèn, số máy tính hoặc sức chứa.");
        } catch (Exception e) {
            showAlert("Lỗi", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    private void clearForm() {
        // Clear basic fields
        roomIdField.clear();
        roomTypeComboBox.setValue(null);
        buildingBlockField.clear();
        areaField.clear();
        numLightBulbsField.clear();
        startDatePicker.setValue(null);
        // Clear specific fields
        hasProjectorCheckBox.setSelected(false);
        numComputersField.clear();
        specializationComboBox.setValue(null);
        capacityField.clear();
        hasSinkCheckBox.setSelected(false);
        // Hide all specific sections
        hideAllSpecificSections();
    }

    private void handleBack() {
        FormNavigator.navigateToForm(backButton.getScene(), "/vn/giadinh/phonghoc/list-room-view.fxml",
                "Quản lý phòng học");
    }

    private RoomDTO createRoomDTO() {
        RoomDTO roomDTO = new RoomDTO();
        // Basic information
        roomDTO.setRoomId(roomIdField.getText());
        roomDTO.setBuildingBlock(buildingBlockField.getText());
        roomDTO.setArea(Double.parseDouble(areaField.getText()));
        roomDTO.setNumLightBulbs(Integer.parseInt(numLightBulbsField.getText()));
        // Convert LocalDate to Date
        LocalDate localDate = startDatePicker.getValue();
        if (localDate != null) {
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            roomDTO.setStartDateOfOperation(date);
        }
        // Room type specific fields
        String selectedRoomType = roomTypeComboBox.getValue();
        if (selectedRoomType != null) {
            if (selectedRoomType.contains(RoomType.LECTURE_HALL.getCode())) {
                roomDTO.setRoomType(RoomType.LECTURE_HALL.getCode());
                roomDTO.setHasProjector(hasProjectorCheckBox.isSelected());
            } else if (selectedRoomType.contains(RoomType.COMPUTER_LAB.getCode())) {
                roomDTO.setRoomType(RoomType.COMPUTER_LAB.getCode());
                roomDTO.setNumComputers(Integer.parseInt(numComputersField.getText()));
            } else if (selectedRoomType.contains(RoomType.LABORATORY.getCode())) {
                roomDTO.setRoomType(RoomType.LABORATORY.getCode());
                roomDTO.setSpecialization(specializationComboBox.getValue());
                roomDTO.setCapacity(Integer.parseInt(capacityField.getText()));
                roomDTO.setHasSink(hasSinkCheckBox.isSelected());
            }
        }
        return roomDTO;
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
    }

}
