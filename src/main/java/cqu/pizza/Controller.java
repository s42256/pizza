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
/**
 * Controller for the Pizza Simulation GUI of Phase 1
 * Responsible for handling user interactions with the buttons
 * @author 12296309
 * @author MD SAKIB UL ISLAM
 */
public class Controller implements Initializable {
    /* Input field for the length of the simulation */
    @FXML private TextField durationField;
    /* Input field for the filename wherer results should be saved */
    @FXML private TextField fileNameField;
    /* Area where messages and reports are displayed*/
    @FXML private TextArea reportArea;
/**
 * Called automatically when the controller is created
 * @param url not used here
 * @param rb  not used here
 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // nothing needed for Phase 1
    }
    /**
     * Handles the Run button.
     * Right now it just shows a placeholder message.
     *
     * @param event the button event
     */
    @FXML
    private void onRun(ActionEvent event) {
        append("Run clicked (Phase 1) – simulation not implemented yet.");
    }
    /**
     * Handles the Reset button.
     * Clears the input fields and the report area.
     *
     * @param event the button event
     */
    @FXML
    private void onReset(ActionEvent event) {
        durationField.clear();
        fileNameField.clear();
        reportArea.clear();
        append("Reset complete.");
    }
    /**
     * Handles the Save button.
     * Shows a placeholder message (saving not added yet).
     *
     * @param event the button event
     */
    @FXML
    private void onSave(ActionEvent event) {
        append("Save clicked (Phase 1) – saving not implemented yet.");
    }
    /**
     * Handles the Exit button.
     * Closes the application window.
     *
     * @param event the button event
     */
    @FXML
    private void onExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    /**
     * Helper method to add text to the report area.
     * Starts a new line if there is already text there.
     *
     * @param line the message to add
     */
    private void append(String line) {
        if (reportArea.getText().isEmpty()) reportArea.appendText(line);
        else reportArea.appendText(System.lineSeparator() + line);
    }
}
