module vn.giadinh.phonghoc {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.sql;
    opens vn.giadinh.phonghoc to javafx.fxml;
    exports vn.giadinh.phonghoc;
    exports vn.giadinh.phonghoc.presentation.view;
    opens vn.giadinh.phonghoc.presentation.view to javafx.fxml;
}