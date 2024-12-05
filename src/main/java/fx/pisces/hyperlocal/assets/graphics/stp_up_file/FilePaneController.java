package fx.pisces.hyperlocal.assets.graphics.stp_up_file;

import fx.pisces.hyperlocal.assets.event.dragevent.ActiveDragState;
import fx.pisces.hyperlocal.assets.graphics.FXObjectController;
import fx.pisces.hyperlocal.utils.keyframe.NodeTransition;
import fx.pisces.hyperlocal.utils.object.NodeUserData;
import fx.pisces.hyperlocal.utils.object.ObservableSharedDataWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;


// Use 90, 90 as pref size
public class FilePaneController extends FXObjectController implements ActiveDragState {
    @FXML
    public StackPane stp_root;

    @FXML
    public GridPane grp_content_pane;
    @FXML
    public ImageView img_content_image;
    @FXML
    public Label lbl_content_name;

    @FXML
    public AnchorPane anp_container_details;
    @FXML
    public Label lbl_content_details;

    @FXML
    public Button btn_content_remove;

    public void enableDraggableFiles(List<File> dataValue) {
        enableDraggableContent(DataFormat.FILES, dataValue);
    }

    public void enableDraggableContent(DataFormat dataType, Object dataValue) {
        System.out.println("---enableDraggableContent");
        // Add the file to the dragboard
        grp_content_pane.setUserData(new NodeUserData(true));

        Dragboard db = stp_root.startDragAndDrop(TransferMode.COPY);

        // Add the file to the dragboard
        ClipboardContent content = new ClipboardContent();
        content.put(dataType, dataValue);
        db.setContent(content);
    }

    @FXML
    public void initialize() {
        grp_content_pane.setUserData(new NodeUserData(false));
        stp_root.getProperties().put("node.property.brand", this.getClass().getName());
    }

    private Object contentDetails;
    private String contentName;

    public void setContentDetails(String text) {
        lbl_content_details.setText(text);
        contentDetails = text;
    }

    public void setContentDetails(File file) {
        contentDetails = file.getAbsolutePath();
        contentName = file.getName();

        lbl_content_details.setText((String) contentDetails);
        lbl_content_name.setText(contentName);
    }

    public void setContentDetails(Path path) {
        File pathFile = path.toFile();
        contentDetails = pathFile.getAbsolutePath();
        contentName = pathFile.getName();

        lbl_content_details.setText((String) contentDetails);
        lbl_content_name.setText(contentName);
    }

    public Object getContentDetails() {
        return contentDetails;
    }

    public void setContentName(String text) {
        lbl_content_name.setText(text);
        contentName = text;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentImage(URI uriImage) {
        Image image = new Image(uriImage.toString());
        img_content_image.setImage(image);
    }

    public void setContentImage(Image image) {
        img_content_image.setImage(image);
    }

    public Image getContentImage() {
        return img_content_image.getImage();
    }

    private void handleEvent(DragEvent event, EventHandler<DragEvent> function) {
        Object userData = grp_content_pane.getUserData();
        if (userData == null) return;
        NodeUserData nodeUserData = (NodeUserData) userData;
        if (nodeUserData.isActiveDraggable()) {
            function.handle(event);
        }
    }

    @Override
    public ActiveDragState onDragEntered(EventHandler<DragEvent> function) {
        grp_content_pane.setOnDragEntered(event -> {
            NodeUserData nodeUserData = (NodeUserData) grp_content_pane.getUserData();
            nodeUserData.setActiveDraggable(true);
            grp_content_pane.setUserData(nodeUserData);

            handleEvent(event, function);
        });
        return this;
    }

    @Override
    public ActiveDragState onDragOver(EventHandler<DragEvent> function) {
        grp_content_pane.setOnDragOver(event -> handleEvent(event, function));
        return this;
    }

    @Override
    public ActiveDragState onDragDropped(EventHandler<DragEvent> function) {
        grp_content_pane.setOnDragDropped(event -> {
            handleEvent(event, function);
            event.setDropCompleted(true);
        });
        return this;
    }

    @Override
    public ActiveDragState onDragExited(EventHandler<DragEvent> function) {
        grp_content_pane.setOnDragExited(event -> {
//            NodeUserData nodeUserData = (NodeUserData) grp_content_pane.getUserData();
//            nodeUserData.setActiveDraggable(false);
//            grp_content_pane.setUserData(nodeUserData);
            handleEvent(event, function);
        });
        return this;
    }

    @Override
    public void onDragComplete(EventHandler<MouseEvent> function) {
        onDragComplete(grp_content_pane, function);
    }

    public void onDragComplete(Node draggable, EventHandler<MouseEvent> function) {
        EventHandler<MouseEvent> onDragDone = event -> {
            NodeUserData nodeUserData = (NodeUserData) grp_content_pane.getUserData();
            nodeUserData.setActiveDraggable(false);
            grp_content_pane.setUserData(nodeUserData);
        };
        draggable.setOnMouseReleased(event -> {
            System.out.println("Drag is done...");
            onDragDone.handle(event);
            function.handle(event);
        });
    }

    public static <Nd extends Node> FilePaneController from(Nd nodeStackPane) throws IllegalArgumentException {
        assert nodeStackPane instanceof StackPane;
        StackPane stackPane = (StackPane) nodeStackPane;
        if (!stackPane.getProperties().get("node.property.brand").equals(FilePaneController.class.getName()))
            throw new IllegalArgumentException("Invalid Node Instance! The node provided may not have been created uniquely from this class");

        FilePaneController controller = new FilePaneController();
        // Retrieve and assign the nodes dynamically
        controller.stp_root = stackPane;

        controller.grp_content_pane = (GridPane) stackPane.lookup("#grp_content_pane");
        controller.img_content_image = (ImageView) stackPane.lookup("#img_content_image");
        controller.lbl_content_name = (Label) stackPane.lookup("#lbl_content_name");
        controller.anp_container_details = (AnchorPane) stackPane.lookup("#anp_container_details");
        controller.lbl_content_details = (Label) stackPane.lookup("#lbl_content_details");

        // Validate all required nodes are found
        if (controller.grp_content_pane == null || controller.img_content_image == null || controller.lbl_content_name == null || controller.anp_container_details == null || controller.lbl_content_details == null) {
            throw new IllegalArgumentException("Some required nodes are missing from the provided pane!");
        }

        return controller;
    }

    public StackPane root() {
        return stp_root;
    }
}
