package vn.giadinh.phonghoc.presentation.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import vn.giadinh.phonghoc.presentation.observer.Subscriber;

import java.net.URL;
import java.util.Arrays;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupRoomTypeComboBox();
        setupSpecializationComboBox();
        setupEventHandlers();
        hideAllSpecificSections();
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
        // Validate specific fields based on room type
        String selectedRoomType = roomTypeComboBox.getValue();
        if (selectedRoomType != null) {
            if (selectedRoomType.contains(LECTURE_HALL)) {
                // No additional validation needed for lecture hall
            } else if (selectedRoomType.contains(COMPUTER_LAB)) {
                if (!validateComputerLabFields()) {
                    showAlert("Lỗi", "Vui lòng điền số máy tính!");
                    return;
                }
            } else if (selectedRoomType.contains(LABORATORY)) {
                if (!validateLaboratoryFields()) {
                    showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin phòng thí nghiệm!");
                    return;
                }
            }
        }
        // TODO: Implement save logic
        showAlert("Thành công", "Phòng học đã được lưu thành công!");
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
