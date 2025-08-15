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
import vn.giadinh.phonghoc.shared.enums.RoomType;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddRoomView implements Initializable, Subscriber {
    private static final List<String> SPECIALIZATIONS = Arrays.asList(
            "Chemistry", "Physics", "Biology", "Computer Science",
            "Environmental Science", "Mathematics", "Engineering");
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
    // MVC Components
    private AddRoomModel model;
    private AddRoomController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComponents();
        setupEventHandlers();
        setupRoomTypeChangeListener();
    }

    private void initializeComponents() {
        // Initialize room type combo box
        roomTypeComboBox.getItems().addAll(
                RoomType.LECTURE_HALL.getDisplayName(),
                RoomType.COMPUTER_LAB.getDisplayName(),
                RoomType.LABORATORY.getDisplayName());
        // Initialize specialization combo box
        specializationComboBox.getItems().addAll(SPECIALIZATIONS);
        // Set default values
        startDatePicker.setValue(LocalDate.now());
        hasProjectorCheckBox.setSelected(false);
        hasSinkCheckBox.setSelected(false);
        // Initially hide all specific sections
        hideAllSpecificSections();
    }

    private void setupEventHandlers() {
        saveButton.setOnAction(e -> handleSave());
        clearButton.setOnAction(e -> handleClear());
        cancelButton.setOnAction(e -> handleCancel());
        backButton.setOnAction(e -> handleBack());

        // Add real-time validation for form fields
        setupFieldValidation();
    }

    private void setupFieldValidation() {
        // Room ID validation
        roomIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            controller.updateRoomField("roomId", newValue);
        });

        // Building block validation
        buildingBlockField.textProperty().addListener((observable, oldValue, newValue) -> {
            controller.updateRoomField("buildingBlock", newValue);
        });

        // Area validation
        areaField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.trim().isEmpty()) {
                    Double area = Double.parseDouble(newValue);
                    controller.updateRoomField("area", area);
                }
            } catch (NumberFormatException e) {
                // Invalid number format
            }
        });

        // Number of light bulbs validation
        numLightBulbsField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.trim().isEmpty()) {
                    Integer numBulbs = Integer.parseInt(newValue);
                    controller.updateRoomField("numLightBulbs", numBulbs);
                }
            } catch (NumberFormatException e) {
                // Invalid number format
            }
        });

        // Room type validation
        roomTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                controller.updateRoomField("roomType", newValue);
            }
        });
    }

    private void setupRoomTypeChangeListener() {
        roomTypeComboBox.setOnAction(e -> {
            String selectedType = roomTypeComboBox.getValue();
            showSpecificSection(selectedType);
        });
    }

    private void showSpecificSection(String roomTypeDisplayName) {
        hideAllSpecificSections();
        if (RoomType.LECTURE_HALL.getDisplayName().equals(roomTypeDisplayName)) {
            lectureHallSection.setVisible(true);
            lectureHallSection.setManaged(true);
        } else if (RoomType.COMPUTER_LAB.getDisplayName().equals(roomTypeDisplayName)) {
            computerLabSection.setVisible(true);
            computerLabSection.setManaged(true);
        } else if (RoomType.LABORATORY.getDisplayName().equals(roomTypeDisplayName)) {
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

    private void handleSave() {
        try {
            RoomDTO roomData = collectFormData();
            controller.executeAddRoom(roomData);
        } catch (Exception e) {
            showError("Lỗi", "Không thể lưu phòng học: " + e.getMessage());
        }
    }

    private RoomDTO collectFormData() throws IllegalArgumentException {
        RoomDTO room = new RoomDTO();
        // Basic fields
        String roomId = roomIdField.getText().trim();
        if (roomId.isEmpty()) {
            throw new IllegalArgumentException("Mã phòng không được để trống");
        }
        room.roomId = roomId;
        String buildingBlock = buildingBlockField.getText().trim();
        if (buildingBlock.isEmpty()) {
            throw new IllegalArgumentException("Tòa nhà không được để trống");
        }
        room.buildingBlock = buildingBlock;
        // Area
        try {
            double area = Double.parseDouble(areaField.getText().trim());
            if (area <= 0) {
                throw new IllegalArgumentException("Diện tích phải là số dương");
            }
            room.area = area;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Diện tích phải là số hợp lệ");
        }
        // Number of light bulbs
        try {
            int numBulbs = Integer.parseInt(numLightBulbsField.getText().trim());
            if (numBulbs < 0) {
                throw new IllegalArgumentException("Số bóng đèn không thể là số âm");
            }
            room.numLightBulbs = numBulbs;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số bóng đèn phải là số nguyên hợp lệ");
        }
        // Start date
        LocalDate startDate = startDatePicker.getValue();
        if (startDate == null) {
            throw new IllegalArgumentException("Ngày đưa vào hoạt động không được để trống");
        }
        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày đưa vào hoạt động không thể là ngày trong tương lai");
        }
        room.startDateOfOperation = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        // Room type
        String selectedType = roomTypeComboBox.getValue();
        if (selectedType == null) {
            throw new IllegalArgumentException("Vui lòng chọn loại phòng");
        }
        // Convert display name to code
        RoomType roomType = RoomType.fromDisplayName(selectedType);
        if (roomType == null) {
            throw new IllegalArgumentException("Loại phòng không hợp lệ");
        }
        room.roomType = roomType.getCode();
        // Specific fields based on room type
        switch (roomType) {
            case LECTURE_HALL:
                room.hasProjector = hasProjectorCheckBox.isSelected();
                break;
            case COMPUTER_LAB:
                try {
                    int numComputers = Integer.parseInt(numComputersField.getText().trim());
                    if (numComputers <= 0) {
                        throw new IllegalArgumentException("Số lượng máy tính phải là số dương");
                    }
                    room.numComputers = numComputers;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Số lượng máy tính phải là số nguyên hợp lệ");
                }
                break;
            case LABORATORY:
                String specialization = specializationComboBox.getValue();
                if (specialization == null || specialization.trim().isEmpty()) {
                    throw new IllegalArgumentException("Chuyên ngành không được để trống");
                }
                room.specialization = specialization;
                try {
                    int capacity = Integer.parseInt(capacityField.getText().trim());
                    if (capacity <= 0) {
                        throw new IllegalArgumentException("Sức chứa phải là số dương");
                    }
                    room.capacity = capacity;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Sức chứa phải là số nguyên hợp lệ");
                }
                room.hasSink = hasSinkCheckBox.isSelected();
                break;
        }
        return room;
    }

    private void handleClear() {
        controller.clearForm();
        clearFormFields();
    }

    private void clearFormFields() {
        roomIdField.clear();
        roomTypeComboBox.setValue(null);
        buildingBlockField.clear();
        areaField.clear();
        numLightBulbsField.clear();
        startDatePicker.setValue(LocalDate.now());
        hasProjectorCheckBox.setSelected(false);
        numComputersField.clear();
        specializationComboBox.setValue(null);
        capacityField.clear();
        hasSinkCheckBox.setSelected(false);
        hideAllSpecificSections();
    }

    private void handleCancel() {
        // Navigate back or close form
        System.out.println("Cancel button clicked");
    }

    private void handleBack() {
        // Navigate back to previous screen
        System.out.println("Back button clicked");
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void update() {
        // Update UI based on model changes
        StatusDTO status = model.getStatus();
        if (status != null) {
            if (status.getStatus() == vn.giadinh.phonghoc.shared.enums.StatusCode.SUCCESS) {
                showSuccess("Thành công", status.getMessage());
                clearFormFields();
            } else if (status.getStatus() == vn.giadinh.phonghoc.shared.enums.StatusCode.FAILURE) {
                showError("Lỗi", status.getMessage());
            }
        }
    }

    public void setModel(AddRoomModel model) {
        this.model = model;
        this.model.addSubscriber(this);
    }

    public void setController(AddRoomController controller) {
        this.controller = controller;
    }
}
