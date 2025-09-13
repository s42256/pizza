package cqu.pizza;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller implements Initializable {
    @FXML private TextField durationField;
    @FXML private TextField fileNameField;
    @FXML private TextArea reportArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // nothing needed for Phase 1
    }

    @FXML
    private void onRun(ActionEvent event) {
        append("Run clicked (Phase 1) – simulation not implemented yet.");
    }

    @FXML
    private void onReset(ActionEvent event) {
        durationField.clear();
        fileNameField.clear();
        reportArea.clear();
        append("Reset complete.");
    }

    @FXML
    private void onSave(ActionEvent event) {
        append("Save clicked (Phase 1) – saving not implemented yet.");
    }

    @FXML
    private void onExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void append(String line) {
        if (reportArea.getText().isEmpty()) reportArea.appendText(line);
        else reportArea.appendText(System.lineSeparator() + line);
    }
}
