module program {
    requires javafx.controls;
    requires javafx.fxml;

    opens program to javafx.fxml;
    exports program;
}
