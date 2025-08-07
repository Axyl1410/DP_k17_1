package vn.giadinh.phonghoc.shared.enums;

import lombok.Getter;
import vn.giadinh.phonghoc.dto.RoomDTO;

@Getter
public enum RoomType {
    LECTURE_HALL("LECTURE_HALL", "Phòng lý thuyết"),
    COMPUTER_LAB("COMPUTER_LAB", "Phòng máy tính"),
    LABORATORY("LABORATORY", "Phòng thí nghiệm");

    private final String code;
    private final String displayName;

    RoomType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * Get RoomType from string code
     *
     * @param code String code
     * @return RoomType or null if not found
     */
    public static RoomType fromCode(String code) {
        if (code == null)
            return null;
        for (RoomType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Get RoomType from display name
     *
     * @param displayName Display name
     * @return RoomType or null if not found
     */
    public static RoomType fromDisplayName(String displayName) {
        if (displayName == null)
            return null;
        for (RoomType type : values()) {
            if (type.displayName.equals(displayName)) {
                return type;
            }
        }
        return null;
    }

    public String getFullDisplayName() {
        return code + " - " + displayName;
    }

    /**
     * Validate room data based on room type
     *
     * @param dto RoomDTO to validate
     * @throws IllegalArgumentException if validation fails
     */
    public void validateRoomData(RoomDTO dto) {
        switch (this) {
            case LECTURE_HALL:
                // No specific validation for lecture hall
                break;
            case COMPUTER_LAB:
                if (dto.getNumComputers() <= 0) {
                    throw new IllegalArgumentException("Phòng máy tính phải có số lượng máy tính là một số dương.");
                }
                break;
            case LABORATORY:
                if (dto.getSpecialization() == null || dto.getSpecialization().trim().isEmpty()) {
                    throw new IllegalArgumentException("Phòng thí nghiệm phải có chuyên ngành.");
                }
                if (dto.getCapacity() <= 0) {
                    throw new IllegalArgumentException("Phòng thí nghiệm phải có sức chứa là một số dương.");
                }
                break;
        }
    }

    @Override
    public String toString() {
        return getFullDisplayName();
    }
}
