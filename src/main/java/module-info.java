module edu.psu.candlestick {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.psu.candlestick to javafx.fxml;
    exports edu.psu.candlestick;
}