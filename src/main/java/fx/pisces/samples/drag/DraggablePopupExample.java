package fx.pisces.samples.drag;

import fx.pisces.hyperlocal.assets.graphics.stp_up_file.FilePane;
import fx.pisces.hyperlocal.assets.graphics.stp_up_file.PopupFileController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class DraggablePopupExample extends Application {

    @Override
    public void start(Stage stage) {
        // Create the Popup
        Popup popup = new Popup();

        PopupFileController popupController = FilePane.asPopup().getController();

        // Create the content for the popup
        StackPane content = new StackPane();
        content.setStyle("-fx-background-color: lightblue; -fx-padding: 20; -fx-border-color: black; -fx-border-width: 1;");
        Text message = new Text("Drag me!");
        Rectangle background = new Rectangle(200, 100, Color.LIGHTBLUE);
        background.setArcWidth(20);
        background.setArcHeight(20);
        content.getChildren().addAll(background, message);

//        popup.getContent().add(content);
        popup.getContent().add(popupController.root());

        final double[] offset = new double[2];
        popupController.root().setOnMousePressed(event -> {
            offset[0] = event.getSceneX();
            offset[1] = event.getSceneY();
        });

        popupController.root().setOnMouseDragged(event -> {
            popup.setX(event.getScreenX() - offset[0]);
            popup.setY(event.getScreenY() - offset[1]);
        });

        // Show the popup
//        content.setOnMouseClicked(event -> {
//            if (!popup.isShowing()) {
//                popup.show(stage, 300, 200);
//            }
//        });

        popupController.root().setOnMouseReleased(event -> {
            System.out.println("Mouse released");
        });

        // Set up the main stage
        StackPane root = new StackPane(new Text("Click to open popup"));
        root.setOnMouseClicked(event -> {
            if (!popup.isShowing()) {
                double screenX = event.getScreenX();
                double screenY = event.getScreenY();
                System.out.println("screenX = " + screenX);
                System.out.println("screenY = " + screenY);

                popup.show(stage, screenX, screenY);
            }
        });

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Draggable Popup Example");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
