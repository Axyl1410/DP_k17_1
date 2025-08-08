package vn.giadinh.phonghoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailDTO {
    private String roomId;
    private String buildingBlock;
    private String area;
    private String numLightBulbs;
    private String startDateOfOperation;
    private String sufficientLight;
    private String isStandard;
    private String roomType;
    private String hasProjector;
    private String numComputers;
    private String specialization;
    private String capacity;
    private String hasSink;

    // Constructor từ ListViewDTO
    public RoomDetailDTO(ListViewDTO listViewDTO) {
        this.roomId = listViewDTO.getRoomId();
        this.buildingBlock = listViewDTO.getBuildingBlock();
        this.area = listViewDTO.getArea();
        this.numLightBulbs = listViewDTO.getNumLightBulbs();
        this.startDateOfOperation = listViewDTO.getStartDateOfOperation();
        this.sufficientLight = listViewDTO.getSufficientLight();
        this.isStandard = listViewDTO.getIsStandard();
    }

    // Phương thức tạo chuỗi hiển thị chi tiết
    public String getDisplayText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Thông tin phòng học:\n\n");
        sb.append("• Mã phòng: ").append(roomId).append("\n");
        sb.append("• Tòa nhà: ").append(buildingBlock).append("\n");
        sb.append("• Diện tích: ").append(area).append(" m²\n");
        sb.append("• Số bóng đèn: ").append(numLightBulbs).append("\n");
        sb.append("• Ngày đưa vào hoạt động: ").append(startDateOfOperation).append("\n");
        sb.append("• Đủ ánh sáng: ").append(sufficientLight).append("\n");
        sb.append("• Đạt chuẩn: ").append(isStandard).append("\n");

        // Thêm thông tin bổ sung nếu có
        if (roomType != null && !roomType.isEmpty()) {
            sb.append("• Loại phòng: ").append(roomType).append("\n");
        }
        if (hasProjector != null && !hasProjector.isEmpty()) {
            sb.append("• Có máy chiếu: ").append(hasProjector).append("\n");
        }
        if (numComputers != null && !numComputers.isEmpty()) {
            sb.append("• Số máy tính: ").append(numComputers).append("\n");
        }
        if (specialization != null && !specialization.isEmpty()) {
            sb.append("• Chuyên ngành: ").append(specialization).append("\n");
        }
        if (capacity != null && !capacity.isEmpty()) {
            sb.append("• Sức chứa: ").append(capacity).append(" người\n");
        }
        if (hasSink != null && !hasSink.isEmpty()) {
            sb.append("• Có bồn rửa: ").append(hasSink).append("\n");
        }

        sb.append("\nBạn có chắc chắn muốn xóa phòng học này?");
        return sb.toString();
    }
}
