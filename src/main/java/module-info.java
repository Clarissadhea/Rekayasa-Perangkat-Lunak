module com.example.classdiagramsimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.classdiagramsimulation to javafx.fxml;
    exports com.example.classdiagramsimulation;
}