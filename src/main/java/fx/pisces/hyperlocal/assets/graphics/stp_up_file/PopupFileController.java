package fx.pisces.hyperlocal.assets.graphics.stp_up_file;

import fx.pisces.hyperlocal.assets.event.dragevent.ActiveDragState;
import fx.pisces.hyperlocal.assets.graphics.FXObjectController;
import fx.pisces.hyperlocal.utils.object.ObservableSharedDataWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

import java.net.URI;

public class PopupFileController extends FXObjectController implements ActiveDragState {
    @FXML
    public StackPane stp_root$ofPopup;
    @FXML
    public ImageView img_content_image$ofPopup;
    @FXML
    public Label lbl_content_details$ofPopup;

    @FXML
    public void initialize() {}

    public void init(Popup popup) {
        final double[] offset = new double[2];
        stp_root$ofPopup.setOnMousePressed(event -> {
            offset[0] = event.getSceneX();
            offset[1] = event.getSceneY();
        });
        stp_root$ofPopup.setOnMouseDragged(event -> {
            popup.setX(event.getScreenX() - offset[0]);
            popup.setY(event.getScreenY() - offset[1]);
        });

        stp_root$ofPopup.setOnMouseReleased(event -> {
            System.out.println("Mouse released");
        });
    }

    public void setContext(String context) {
        lbl_content_details$ofPopup.setText(context);
    }

    public String getContext() {
        return lbl_content_details$ofPopup.getText();
    }

    public void setContentImage(URI uriImage) {
        Image image = new Image(uriImage.toString());
        img_content_image$ofPopup.setImage(image);
    }

    public void setContentImage(Image image) {
        img_content_image$ofPopup.setImage(image);
    }

    @Override
    public Node root() {
        return stp_root$ofPopup;
    }

    public void onMousePressed(EventHandler<MouseEvent> function) {
        stp_root$ofPopup.setOnMousePressed(function);
    }

    @Override
    public ActiveDragState onDragEntered(EventHandler<DragEvent> function) {
        stp_root$ofPopup.setOnDragEntered(function);
        return this;
    }

    @Override
    public ActiveDragState onDragOver(EventHandler<DragEvent> function) {
        stp_root$ofPopup.setOnDragOver(function);
        return this;
    }

    @Override
    public ActiveDragState onDragDropped(EventHandler<DragEvent> function) {
        stp_root$ofPopup.setOnDragDropped(event -> {
            function.handle(event);
            event.setDropCompleted(true);
        });
        return this;
    }

    @Override
    public ActiveDragState onDragExited(EventHandler<DragEvent> function) {
        ObservableSharedDataWrapper<Boolean> onDragExited = new ObservableSharedDataWrapper<>(false);
        stp_root$ofPopup.setOnDragExited(event -> {
            if (!onDragExited.get() && !stp_root$ofPopup.contains(event.getX(), event.getY())) {
                onDragExited.set(true);
                function.handle(event);
            }
        });
        return this;
    }

    @Override
    public void onDragComplete(EventHandler<MouseEvent> function) {
        stp_root$ofPopup.setOnMouseReleased(event -> {
            System.out.println("Drag is done...");
            function.handle(event);
        });
    }
}
