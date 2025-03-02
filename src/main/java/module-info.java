module com.example.library_management_desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens controllers;
    opens service;
    opens models;
    opens repositories;
}