module com.cadastro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;

    opens com.cadastro to javafx.fxml;
    opens com.cadastro.model to javafx.base;

    exports com.cadastro;
    exports com.cadastro.config;
    opens com.cadastro.config to javafx.fxml;
}