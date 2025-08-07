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
import vn.giadinh.phonghoc.shared.enums.StatusCode;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddRoomView implements Initializable, Subscriber {
    // Room type constants
    private static final String LECTURE_HALL = "LECTURE_HALL";
    private static final String COMPUTER_LAB = "COMPUTER_LAB";
    private static final String LABORATORY = "LABORATORY";
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
        // Add room type options
        roomTypeComboBox.getItems().addAll(
                "LECTURE_HALL - Phòng lý thuyết",
                "COMPUTER_LAB - Phòng máy tính",
                "LABORATORY - Phòng thí nghiệm");
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
        if (selectedItem.contains(LECTURE_HALL)) {
            lectureHallSection.setVisible(true);
            lectureHallSection.setManaged(true);
        } else if (selectedItem.contains(COMPUTER_LAB)) {
            computerLabSection.setVisible(true);
            computerLabSection.setManaged(true);
        } else if (selectedItem.contains(LABORATORY)) {
            laboratorySection.setVisible(true);
            laboratorySection.setManaged(true);
        }
    }

    private void hideAllSpecificSections() {
        lectureHallSection.setVisible(false);
        lectureHallSection.setManaged(false);
        computerLabSection.setVisible(false);
        computerLabSection.setManaged(false);
        laboratorySection.setVisible(false);
        laboratorySection.setManaged(false);
    }

    private void handleSaveRoom() {
        // Validate required fields
        if (!validateBasicFields()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin cơ bản!");
            return;
        }
        RoomDTO roomDTO = createRoomDTO();
        model.statusDTO = AddRoomController.execute(roomDTO);
        StatusDTO statusdto = model.statusDTO;
        StatusCode status = statusdto.getStatus();
        String message = statusdto.getMessage();
        if (status.equals(StatusCode.SUCCESS)) {
            showAlert("Thành công", "Phòng học đã được lưu thành công!");
            clearForm();
        } else {
            showAlert("Lỗi", "Không thể lưu phòng học: " + message);
        }
    }

    private boolean validateBasicFields() {
        return roomIdField.getText() != null && !roomIdField.getText().trim().isEmpty() &&
                roomTypeComboBox.getValue() != null &&
                buildingBlockField.getText() != null && !buildingBlockField.getText().trim().isEmpty() &&
                areaField.getText() != null && !areaField.getText().trim().isEmpty() &&
                numLightBulbsField.getText() != null && !numLightBulbsField.getText().trim().isEmpty() &&
                startDatePicker.getValue() != null;
    }

    private boolean validateComputerLabFields() {
        return numComputersField.getText() != null && !numComputersField.getText().trim().isEmpty();
    }

    private boolean validateLaboratoryFields() {
        return specializationComboBox.getValue() != null &&
                capacityField.getText() != null && !capacityField.getText().trim().isEmpty();
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
        // Close the current form window
        javafx.stage.Stage stage = (javafx.stage.Stage) backButton.getScene().getWindow();
        stage.close();
    }

    private RoomDTO createRoomDTO() {
        RoomDTO roomDTO = new RoomDTO();
        // Basic information
        roomDTO.setRoomId(roomIdField.getText().trim());
        roomDTO.setBuildingBlock(buildingBlockField.getText().trim());
        roomDTO.setArea(Double.parseDouble(areaField.getText().trim()));
        roomDTO.setNumLightBulbs(Integer.parseInt(numLightBulbsField.getText().trim()));
        // Convert LocalDate to Date
        LocalDate localDate = startDatePicker.getValue();
        if (localDate != null) {
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            roomDTO.setStartDateOfOperation(date);
        }
        // Room type specific fields
        String selectedRoomType = roomTypeComboBox.getValue();
        if (selectedRoomType != null) {
            if (selectedRoomType.contains(LECTURE_HALL)) {
                roomDTO.setRoomType("LECTURE_HALL");
                roomDTO.setHasProjector(hasProjectorCheckBox.isSelected());
            } else if (selectedRoomType.contains(COMPUTER_LAB)) {
                roomDTO.setRoomType("COMPUTER_LAB");
                roomDTO.setNumComputers(Integer.parseInt(numComputersField.getText().trim()));
            } else if (selectedRoomType.contains(LABORATORY)) {
                roomDTO.setRoomType("LABORATORY");
                roomDTO.setSpecialization(specializationComboBox.getValue());
                roomDTO.setCapacity(Integer.parseInt(capacityField.getText().trim()));
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
        // Handle model updates if needed
        StatusDTO status = AddRoomController.addRoomModel.statusDTO;
        if (status != null) {
            System.out.println("Model updated - Status: " + status.getStatus() + ", Message: " + status.getMessage());
        }
    }

}
