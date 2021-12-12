module warehouse.warehouse {
    requires java.sql;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires mysql.connector.java;


    opens warehouse.warehouse to javafx.fxml;
    exports warehouse.warehouse;
}