package fx.pisces.hyperlocal.assets.graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.function.Consumer;

public abstract class FXComponent {
    protected FXMLLoader fxmlLoader;
    protected FXComponent(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    private Node node;
    public FXComponent(Node node) {
        this.node = node;
    }

    public static FXComponent builder(Node node) {
        return new Builder(node);
    }

    public void onFileDropped(Consumer<File> consumeFunction) {
        // Set up drag and drop event handlers
        node.setOnDragOver(event -> {
            if (event.getGestureSource() != node && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        node.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasFiles()) {
                success = true;
                List<File> dragboardFiles = dragboard.getFiles();
                dragboardFiles.forEach(consumeFunction);
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    public static class Builder extends FXComponent {
        public Builder(@NotNull Node node) {
            super(node);
        }

    }
}
