module vn.giadinh.phonghoc {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    opens vn.giadinh.phonghoc to javafx.fxml;
    exports vn.giadinh.phonghoc;
}