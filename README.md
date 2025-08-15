# Hệ Thống Quản Lý Phòng Học - Chức Năng Thêm Phòng

## Tổng Quan

Dự án này triển khai chức năng thêm phòng học mới theo mô hình kiến trúc MVC (Model-View-Controller) với JavaFX và Java 17+.

## Kiến Trúc 3-Layer Architecture

### 1. Model (AddRoomModel)

- **Vị trí**: `src/main/java/vn/giadinh/phonghoc/presentation/model/AddRoomModel.java`
- **Chức năng**:
  - Quản lý dữ liệu phòng học
  - Xử lý trạng thái form
  - Thông báo thay đổi cho các observer
  - **Không chứa business logic** (tuân thủ 3-layer architecture)

### 2. View (AddRoomView)

- **Vị trí**: `src/main/java/vn/giadinh/phonghoc/presentation/view/AddRoomView.java`
- **FXML**: `src/main/resources/vn/giadinh/phonghoc/add-room-view.fxml`
- **Chức năng**:
  - Hiển thị giao diện người dùng
  - Xử lý sự kiện từ người dùng
  - Validation dữ liệu đầu vào
  - Hiển thị thông báo lỗi/thành công

### 3. Controller (AddRoomController)

- **Vị trí**: `src/main/java/vn/giadinh/phonghoc/presentation/controller/AddRoomController.java`
- **Chức năng**:
  - Điều phối giữa Model và View
  - **Field update coordination** (được chuyển từ Model)
  - **Form validation coordination** (gọi UseCase)
  - Xử lý logic nghiệp vụ
  - Validation dữ liệu
  - Gọi UseCase để thực hiện thao tác

### 4. UseCase (AddRoomUsecase)

- **Vị trí**: `src/main/java/vn/giadinh/phonghoc/business/usecase/AddRoomUsecase.java`
- **Chức năng**:
  - Chứa logic nghiệp vụ chính
  - **Form validation logic** (được chuyển từ Model)
  - **Field update logic** (được chuyển từ Model)
  - Validation dữ liệu theo quy tắc nghiệp vụ
  - Gọi Gateway để lưu dữ liệu
  - Trả về kết quả xử lý

### 5. Gateway & DAO

- **Gateway**: `src/main/java/vn/giadinh/phonghoc/persistence/gateway/AddRoomGateway.java`
- **DAO**: `src/main/java/vn/giadinh/phonghoc/persistence/dao/AddRoomDAO.java`
- **Chức năng**: Tương tác với cơ sở dữ liệu

## Các Loại Phòng Học

### 1. Phòng Lý Thuyết (Lecture Hall)

- **Trường đặc biệt**: Có máy chiếu hay không
- **Validation**: Không có validation đặc biệt

### 2. Phòng Máy Tính (Computer Lab)

- **Trường đặc biệt**: Số lượng máy tính
- **Validation**: Số máy tính phải > 0

### 3. Phòng Thí Nghiệm (Laboratory)

- **Trường đặc biệt**:
  - Chuyên ngành
  - Sức chứa
  - Có bồn rửa hay không
- **Validation**:
  - Chuyên ngành không được để trống
  - Sức chứa phải > 0

## Cách Sử Dụng

### 1. Khởi Chạy Ứng Dụng

```bash
# Biên dịch dự án
mvn clean compile

# Chạy ứng dụng
mvn javafx:run
```

### 2. Quy Trình Thêm Phòng

1. **Nhập thông tin cơ bản**:

   - Mã phòng (bắt buộc)
   - Loại phòng (bắt buộc)
   - Dãy nhà (bắt buộc)
   - Diện tích (bắt buộc, > 0)
   - Số bóng đèn (bắt buộc, >= 0)
   - Ngày bắt đầu hoạt động (bắt buộc, không được là ngày tương lai)

2. **Chọn loại phòng**: Giao diện sẽ hiển thị các trường đặc biệt tương ứng

3. **Nhập thông tin chi tiết** theo loại phòng đã chọn

4. **Lưu phòng học**: Hệ thống sẽ validate và lưu vào cơ sở dữ liệu

### 3. Các Nút Chức Năng

- **Lưu phòng học**: Lưu thông tin phòng học mới
- **Xóa form**: Xóa tất cả dữ liệu đã nhập
- **Hủy**: Hủy bỏ thao tác thêm phòng
- **Quay lại**: Quay về màn hình trước đó

## Validation

### Validation Cơ Bản

- Tất cả các trường bắt buộc không được để trống
- Diện tích phải là số dương
- Số bóng đèn không thể là số âm
- Ngày bắt đầu hoạt động không thể là ngày trong tương lai

### Validation Theo Loại Phòng

- **Phòng máy tính**: Số lượng máy tính phải > 0
- **Phòng thí nghiệm**: Chuyên ngành không được để trống, sức chứa phải > 0

## Cấu Trúc Cơ Sở Dữ Liệu

Bảng `rooms` chứa các trường:

- `room_id`: Mã phòng (Primary Key)
- `room_type`: Loại phòng
- `building_block`: Dãy nhà
- `area_sqm`: Diện tích (m²)
- `num_light_bulbs`: Số bóng đèn
- `start_operation_date`: Ngày bắt đầu hoạt động
- `has_projector`: Có máy chiếu (phòng lý thuyết)
- `num_computers`: Số máy tính (phòng máy tính)
- `specialization`: Chuyên ngành (phòng thí nghiệm)
- `capacity`: Sức chứa (phòng thí nghiệm)
- `has_sink`: Có bồn rửa (phòng thí nghiệm)

## Xử Lý Lỗi

### Các Loại Lỗi

1. **Lỗi dữ liệu**: Dữ liệu không hợp lệ
2. **Lỗi cơ sở dữ liệu**: Lỗi kết nối hoặc truy vấn
3. **Lỗi không xác định**: Các lỗi khác

### Hiển Thị Lỗi

- Sử dụng Alert dialog để hiển thị thông báo lỗi
- Thông báo rõ ràng về nguyên nhân lỗi
- Hướng dẫn cách khắc phục

## Mở Rộng

### Thêm Loại Phòng Mới

1. Thêm enum mới vào `RoomType.java`
2. Cập nhật validation logic trong `validateRoomData()`
3. Thêm trường mới vào `RoomDTO.java`
4. Cập nhật giao diện FXML
5. Cập nhật logic xử lý trong View

### Thêm Validation Mới

1. Cập nhật method `validateRoom()` trong `AddRoomUsecase.java`
2. Cập nhật method `validateFormData()` trong `AddRoomUsecase.java`
3. Cập nhật logic validation trong View

## Yêu Cầu Hệ Thống

- Java 17 hoặc cao hơn
- JavaFX 17+
- Maven 3.6+
- Cơ sở dữ liệu SQL (MySQL, PostgreSQL, hoặc H2)

## Tác Giả

Dự án được phát triển theo mô hình Clean Architecture và SOLID principles.
