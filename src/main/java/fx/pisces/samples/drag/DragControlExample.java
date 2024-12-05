package fx.pisces.samples.drag;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DragControlExample extends Application {

    private Node activeDragNode = null; // Tracks the active node being dragged

    @Override
    public void start(Stage stage) {
        FlowPane flp_upload_pane = new FlowPane();

        // Create multiple nodes
        for (int i = 0; i < 5; i++) {
            Pane controller = createDraggableNode("Node " + i);
            flp_upload_pane.getChildren().add(controller);
        }

        Scene scene = new Scene(flp_upload_pane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Drag Control Example");
        stage.show();
    }

    private Pane createDraggableNode(String name) {
        Pane pane = new Pane();
        Rectangle rect = new Rectangle(100, 100, Color.LIGHTBLUE);
        rect.setStroke(Color.BLACK);

        // Set drag detection
        rect.setOnDragDetected(event -> {
            if (activeDragNode != null) return; // Skip if another drag is active

            activeDragNode = rect; // Set the active drag node
            rect.startDragAndDrop(TransferMode.ANY);
            rect.setFill(Color.DARKBLUE); // Indicate active drag
            event.consume();
        });

        // Handle drag exit
        rect.setOnDragExited(event -> {
            if (rect == activeDragNode) {
                rect.setFill(Color.LIGHTBLUE); // Reset style
            }
            event.consume();
        });

        // Handle drag done
        rect.setOnDragDone(event -> {
            if (rect == activeDragNode) {
                activeDragNode = null; // Reset the active drag node
                rect.setFill(Color.LIGHTBLUE); // Reset style
            }
            event.consume();
        });

        pane.getChildren().add(rect);
        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
