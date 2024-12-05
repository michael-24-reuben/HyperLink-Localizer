package fx.pisces.samples.drag;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DragExitExample extends Application {
    @Override
    public void start(Stage stage) {
        FlowPane root = new FlowPane();
        Circle circle1 = new Circle(200, 200, 50, Color.LIGHTBLUE); // A stationary circle
        Circle circle2 = new Circle(200, 200, 50, Color.LIGHTBLUE); // A stationary circle
        extracted(circle1);
        extracted(circle2);

        root.getChildren().add(circle1);
        root.getChildren().add(circle2);
        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Drag Exit Example");
        stage.show();
    }

    private static void extracted(Circle circle) {
        boolean[] isDragging = {false}; // Track if the circle is being dragged

        // Event handlers
        circle.setOnMousePressed(event -> isDragging[0] = true);

        circle.setOnMouseDragged(event -> {
            if (!circle.contains(event.getX(), event.getY())) {
                System.out.println("Exited the circle while dragging");
                isDragging[0] = false; // Stop tracking drag after exiting
            }
        });

        circle.setOnMouseReleased(event -> {
            if (isDragging[0]) {
                System.out.println("Drag ended inside the circle");
                isDragging[0] = false; // Reset dragging state
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
