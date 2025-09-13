module cqu.pizza {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens cqu.pizza to javafx.fxml;
    exports cqu.pizza;
}
