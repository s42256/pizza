package cqu.pizza;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.events.OrderEvent;
import cqu.pizza.lifecycle.events.ReportEvent;
import cqu.pizza.simulator.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Pizza Simulation GUI (Phase 1). Handles button clicks and
 * updates the text fields and report area.
 *
 * @author 12296309
 * @author MD SAKIB UL ISLAM
 */
public class Controller implements Initializable {

    @FXML private TextField durationField;
    @FXML private TextField fileNameField;
    @FXML private TextArea reportArea;

    /** Called after the FXML is loaded. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // no extra setup required for Phase 1–2
    }

    /**
     * Starts the simulation when the Run button is clicked.
     *
     * @param event action event from the Run button
     */
    @FXML
    private void onRun(ActionEvent event) {
        try {
            int duration = Integer.parseInt(durationField.getText().trim());
            if (duration <= 0) {
                showMessage("Duration entered must be a positive integer.");
                return;
            }

            Model model = new Model();
            Simulator sim = new Simulator(model);

            OrderEvent first = new OrderEvent(model.nextRequest());
            ReportEvent report = new ReportEvent(duration);

            sim.initialize(first, report);
            sim.run(duration);

            showMessage("Simulation complete. See console for the trace.");
        } catch (NumberFormatException ex) {
            showMessage("Duration entered must be a positive integer.");
        } catch (Exception ex) {
            showMessage("Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Clears input fields and report area.
     *
     * @param event action event from the Reset button
     */
    @FXML
    private void onReset(ActionEvent event) {
        durationField.clear();
        fileNameField.clear();
        reportArea.clear();
        showMessage("Reset complete.");
    }

    /**
     * Placeholder for Phase 5 file save.
     *
     * @param event action event from the Save button
     */
    @FXML
    private void onSave(ActionEvent event) {
        showMessage("Save clicked (Phase 1–2) – not implemented yet.");
    }

    /**
     * Closes the application window.
     *
     * @param event action event from the Exit button
     */
    @FXML
    private void onExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Replaces the content of the report area with a message.
     *
     * @param message text to display
     */
    private void showMessage(String message) {
        reportArea.setText(message);
    }
}
