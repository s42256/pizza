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
 * Controller for the Pizza Simulation GUI (Phase 1). Handles button clicks and
 * updates the text fields and report area.
 *
 * @author 12296309
 * @author MD SAKIB UL ISLAM
 */
public class Controller implements Initializable {

    // Input for the length of the simulation
    @FXML
    private TextField durationField;

    // Input for the filename where results should be saved
    @FXML
    private TextField fileNameField;

    // Area where messages and the summary report are shown
    @FXML
    private TextArea reportArea;

    /**
     * Runs after the FXML is loaded.
     *
     * @param url not used
     * @param rb not used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No setup needed for Phase 1
    }

    /**
     * Run button handler (Phase 1 stub).
     *
     * @param event the button event
     */
    @FXML
    private void onRun(ActionEvent event) {
        try {
            int duration = Integer.parseInt(durationField.getText().trim());
            if (duration <= 0) {
                showMessage("Please enter a positive integer for duration.");
                return;
            }

            // build model + simulator
            cqu.pizza.lifecycle.Model model = new cqu.pizza.lifecycle.Model();
            cqu.pizza.simulator.Simulator sim = new cqu.pizza.simulator.Simulator(model);

            // initialize with first order event and report event
            cqu.pizza.lifecycle.events.OrderEvent first
                    = new cqu.pizza.lifecycle.events.OrderEvent(model.nextRequest());
            cqu.pizza.lifecycle.events.ReportEvent report
                    = new cqu.pizza.lifecycle.events.ReportEvent(duration);

            sim.initialize(first, report);
            sim.run(duration);

            showMessage("Simulation complete. See console for the trace.");
        } catch (NumberFormatException ex) {
            showMessage("Duration must be a valid integer.");
        } catch (Exception ex) {
            showMessage("Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Reset button handler. Clears inputs and the report area.
     *
     * @param event the button event
     */
    @FXML
    private void onReset(ActionEvent event) {
        durationField.clear();
        fileNameField.clear();
        reportArea.clear();
        showMessage("Reset complete.");
    }

    /**
     * Save button handler (Phase 1 stub).
     *
     * @param event the button event
     */
    @FXML
    private void onSave(ActionEvent event) {
        showMessage("Save clicked (Phase 1) – saving not implemented yet.");
    }

    /**
     * Exit button handler. Closes the window.
     *
     * @param event the button event
     */
    @FXML
    private void onExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Replaces the contents of the report area with a new message.
     *
     * @param message the text to display
     */
    private void showMessage(String message) {
        reportArea.setText(message);
    }
}
