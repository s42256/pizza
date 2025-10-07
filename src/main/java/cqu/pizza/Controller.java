package cqu.pizza;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Report;
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
import cqu.pizza.lifecycle.ReportException;


/**
 * Controller for the Pizza Simulation GUI. Handles button clicks and
 * updates the text fields and report area.
 *
 * @author sisak
 */
public class Controller implements Initializable {

    @FXML private TextField durationField;
    @FXML private TextField fileNameField;
    @FXML private TextArea reportArea;

    private Model model;
    private Report report;

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
        reportArea.clear();
        try {
            int duration = Integer.parseInt(durationField.getText().trim());
            if (duration <= 0) {
                showMessage("Duration entered must be a positive integer.");
                return;
            }

            model = new Model();
            Simulator sim = new Simulator(model);

            OrderEvent first = new OrderEvent(model.nextRequest());
            ReportEvent reportEvent = new ReportEvent(duration);

            sim.initialize(first, reportEvent);
            sim.run(duration);

            // Phase 4+: get the generated report and display it
            report = model.getReport();
            if (report != null) {
                reportArea.setText(report.getText());
            } else {
                showMessage("No report generated.");
            }
        } catch (NumberFormatException ex) {
            showMessage("Duration entered must be a positive integer.");
        } catch (Exception ex) {
            showMessage("Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Saves the report to the file specified in the filename field.
     * Validates that a report exists and that a filename has been entered.
     *
     * @param event action event from the Save button
     */
    @FXML
    private void onSave(ActionEvent event) {
        String filename = (fileNameField.getText() == null) ? "" : fileNameField.getText().trim();

        if (filename.isEmpty()) {
            showMessage("Please enter a filename before saving.");
            return;
        }
        if (report == null) {
            showMessage("There is no report to save. Run the simulation first.");
            return;
        }

        try {
            report.save(filename);
            showMessage("Report saved to " + filename);
        } catch (ReportException re) {
            showMessage(re.getMessage());
        } catch (Exception ex) {
            showMessage("Unexpected error while saving: " + ex.getMessage());
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
