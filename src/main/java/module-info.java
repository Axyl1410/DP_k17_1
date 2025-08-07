module vn.giadinh.phonghoc {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.sql;
    opens vn.giadinh.phonghoc to javafx.fxml;
    exports vn.giadinh.phonghoc;
    exports vn.giadinh.phonghoc.presentation.view;
    exports vn.giadinh.phonghoc.presentation.model;
    exports vn.giadinh.phonghoc.presentation.controller;
    exports vn.giadinh.phonghoc.presentation.observer;
    exports vn.giadinh.phonghoc.dto;
    exports vn.giadinh.phonghoc.business.usecase;
    exports vn.giadinh.phonghoc.business.factory;
    exports vn.giadinh.phonghoc.entity;
    exports vn.giadinh.phonghoc.persistence.dao;
    opens vn.giadinh.phonghoc.presentation.view to javafx.fxml;
    opens vn.giadinh.phonghoc.presentation.model to javafx.fxml;
    opens vn.giadinh.phonghoc.presentation.controller to javafx.fxml;
    exports vn.giadinh.phonghoc.shared.common;
    opens vn.giadinh.phonghoc.shared.common to javafx.fxml;
}