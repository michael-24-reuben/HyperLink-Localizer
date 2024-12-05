package fx.pisces.samples.drag;

import fx.pisces.hyperlocal.utils.AppManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DragDropAreaExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a label to act as the drop area
        Label dropArea = new Label("Drag and drop a file here");
        dropArea.setStyle("-fx-border-color: #9eec97; -fx-border-width: 2; -fx-padding: 20; -fx-background-color: #8f8f8f;");
        dropArea.setPrefSize(300, 200);

        /*// Set up drag and drop event handlers
        dropArea.setOnDragOver(event -> {
            if (event.getGestureSource() != dropArea && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        dropArea.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasFiles()) {
                success = true;
                dragboard.getFiles().forEach(file -> {
                    System.out.println("File dropped: " + file.getAbsolutePath());
                    // Handle the dropped file
                });
            }

            event.setDropCompleted(success);
            event.consume();
        });*/
        AppManager.OnFileDropped(dropArea, System.out::println, Color.GOLD, Color.BEIGE);

        // Set up the scene
        StackPane root = new StackPane(dropArea);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("JavaFX Drag and Drop Area");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
