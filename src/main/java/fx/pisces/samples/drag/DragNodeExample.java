package fx.pisces.samples.drag;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DragNodeExample extends Application {
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Circle draggableNode = new Circle(50, 50, 20); // A circle as the draggable node

        // Event handlers for dragging
        draggableNode.setOnMousePressed(event -> {
            draggableNode.setUserData(new double[]{event.getSceneX(), event.getSceneY()});
        });

        draggableNode.setOnMouseDragged(event -> {
            double[] startPos = (double[]) draggableNode.getUserData();
            double offsetX = event.getSceneX() - startPos[0];
            double offsetY = event.getSceneY() - startPos[1];
            draggableNode.setTranslateX(draggableNode.getTranslateX() + offsetX);
            draggableNode.setTranslateY(draggableNode.getTranslateY() + offsetY);
            draggableNode.setUserData(new double[]{event.getSceneX(), event.getSceneY()});
        });

        draggableNode.setOnMouseReleased(event -> {
            System.out.println("Drag is finished");
        });


        root.getChildren().add(draggableNode);
        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Draggable Node Example");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
