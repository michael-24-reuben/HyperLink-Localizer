
package fx.pisces.samples.launch;

import fx.pisces.hyperlocal.utils.props.app.ApplicationProperties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

/*
* Prompts user to choose from an `fxml` file and displays a Stage of that app
*/

public class StageLauncher extends Application {
    private ApplicationProperties appProperties;

    @Override
    public void start(Stage primaryStage) {
        appProperties = new ApplicationProperties();

        // Initial launch: show file chooser to pick an FXML file
        primaryStage.setTitle("FXML Stage Launcher");
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        String defaultInitialPath = appProperties.computeProperty("fxml.load.select.parent");
        File defaultInitialFile = new File(defaultInitialPath);
        if (!defaultInitialFile.exists()) {
            System.out.println("Initial file path does not exist: " + defaultInitialPath);
            System.out.println("Path ignored...\n");
            defaultInitialFile = new File(System.getProperty("user.home") + File.separator + "Desktop");
        }

        System.out.println("Loading File Chooser...\n");
        // Set up FileChooser to select .fxml files
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open FXML File");
        fileChooser.setInitialDirectory(defaultInitialFile);
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("FXML files (*.fxml)", "*.fxml")
        );


        // Let the user choose an FXML file
        File fxmlFile = fileChooser.showOpenDialog(primaryStage);
        if (fxmlFile != null) {
            System.out.println("Loading FXML: \033[3;35m" + fxmlFile.getAbsolutePath() + " \033[0m\n");
            // Load the selected FXML file into a new Stage
            loadFXMLInNewStage(fxmlFile, primaryStage);
        } else {
            System.out.println("Exiting Program on \033[1;31mnull\033[0m choice");
            // No file was selected, close the application
            primaryStage.close();
            Platform.exit();
        }
    }

    /**
     * Loads the given FXML file into a new stage.
     * @param fxmlFile File to load as an FXML file.
     * @param stage The primary stage to use or reset.
     */
    private void loadFXMLInNewStage(File fxmlFile, Stage stage) {
        try {
            appProperties.setProperty("fxml.load.select.parent", fxmlFile.getAbsoluteFile().getParent());
            appProperties.store();
            // Load the FXML file into a Parent node
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = fxmlLoader.load();

            // Create a new scene and set it to the stage
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);
            stage.setTitle("Loaded: " + fxmlFile.getName());

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors in loading the FXML file (like showing an alert)
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
