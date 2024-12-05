package fx.pisces.samples.drag;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class SeamlessPopupDrag extends Application {

    private double dragOffsetX;
    private double dragOffsetY;

    @Override
    public void start(Stage primaryStage) {
        Label node = new Label("Press and drag me");
        node.setStyle("-fx-background-color: lightblue; -fx-padding: 10;");

        StackPane root = new StackPane(node);
        Popup popup = createDraggablePopup();

        node.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                // Show popup at the initial press location
                dragOffsetX = event.getScreenX();
                dragOffsetY = event.getScreenY();
                popup.show(primaryStage, dragOffsetX, dragOffsetY);
            }
        });

        node.setOnMouseDragged(event -> {
            if (popup.isShowing()) {
                // Update popup position dynamically
                double offsetX = event.getScreenX() - dragOffsetX;
                double offsetY = event.getScreenY() - dragOffsetY;
                popup.setX(popup.getX() + offsetX);
                popup.setY(popup.getY() + offsetY);
                dragOffsetX = event.getScreenX();
                dragOffsetY = event.getScreenY();
            }
        });

        node.setOnMouseReleased(event -> popup.hide());

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Seamless Popup Drag");
        primaryStage.show();
    }

    private Popup createDraggablePopup() {
        Popup popup = new Popup();
        Label popupContent = new Label("I'm draggable!");
        popupContent.setStyle("-fx-background-color: lightgray; -fx-padding: 10;");
        popup.getContent().add(popupContent);
        return popup;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
