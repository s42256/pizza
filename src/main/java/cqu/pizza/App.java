package cqu.pizza;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 
 * @author 12296309
 * @author MD SAKIB UL ISLAM
 */
public class App extends Application {
/**The Scene that holds the current GUI Layout. */
    private static Scene scene;
    /**
     * Starts the program by loading the GUI From the FXML file
     * attaching it to a scene. and shjowing it in the main window.
     * @param stage the first window created by JavaFX
     * @throws IOException  if the FXML File cannot be loaded
     */

    @Override
    public void start(Stage stage) throws IOException {
        // Load GUI From FXML and set size of the Scene
        scene = new Scene(loadFXML("FXML"));
        stage.setTitle("Simulation");   // title for the window
        stage.setScene(scene); // attach the scene to the stage
        stage.show(); // make the stage visible
    }
/**
 * Changes the root of the current scene to aa new FXML Layout
 * @param fxml 
 * @throws IOException if the file cannot be found or opened
 */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
/**
 * Loads the given FXML file and returns its root element.
 * @param fxml the file nanme without .fxml extension
 * @return the root node of the loaded FXML
 * @throws IOException  if the file cannot be loaded
 */
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
/**
 * Launces the JavaFX application
 * @param args 
 */
    public static void main(String[] args) {
        launch();
    }

}