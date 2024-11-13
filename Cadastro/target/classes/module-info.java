module com.cadastro {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cadastro.model to javafx.base;
    exports com.cadastro.model;
    exports com.cadastro.controller;
}