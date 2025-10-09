package cqu.pizza;

import cqu.pizza.lifecycle.Model;
import cqu.pizza.lifecycle.Report;
import cqu.pizza.lifecycle.ReportException;
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
 * JavaFX controller for the Pizza Simulation GUI.
 * Validates input, runs the simulation, shows the report, and saves it to a file.
 */

/**
 * @author sisak
 */
public class Controller implements Initializable {

    @FXML private TextField durationField;
    @FXML private TextField fileNameField;
    @FXML private TextArea reportArea;

    private Model model;
    private Report report;

    /**
     * Called by JavaFX after the FXML has been loaded.
     *
     * @param location location used to resolve relative paths (unused)
     * @param resources resources used for localization (unused)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // no extra setup required
    }

    /**
     * Handles the Run button: validates the duration, creates a model/simulator,
     * runs the simulation, and displays the generated report text.
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
     * Handles the Save button: validates a file name and that a report exists,
     * then writes the report to disk and shows a status message.
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
     * Handles the Reset button: clears input fields and the report area,
     * and shows a confirmation message.
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
     * Handles the Exit button: closes the application window.
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
