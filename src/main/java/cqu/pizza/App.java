package cqu.pizza;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX application entry point for the Pizza Simulation.
 * Loads the FXML, creates the primary stage, and shows the UI.
 */

/**
 * @author sisak
 */
public class App extends Application {

    /** Scene that displays the current GUI layout. */
    private static Scene scene;

    /**
     * Loads the initial FXML and shows the primary stage.
     *
     * @param stage the primary stage created by JavaFX
     * @throws IOException if the FXML cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("FXML"));
        stage.setTitle("Simulation");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Replaces the current scene root with the given FXML layout.
     *
     * @param fxml base name of the FXML file (without extension)
     * @throws IOException if the FXML cannot be loaded
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads an FXML file and returns its root node.
     *
     * @param fxml base name of the FXML file
     * @return the root {@link Parent} node of the loaded FXML
     * @throws IOException if the FXML cannot be loaded
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        launch();
    }
}
