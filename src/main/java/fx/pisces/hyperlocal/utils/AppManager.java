package fx.pisces.hyperlocal.utils;

import fx.pisces.hyperlocal.assets.graphics.FXObject;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppManager {
    public static File showFileChooser(@Nullable Stage stage, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title == null ? "Select File" : title);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return file.getAbsoluteFile();
        } else {
            return null;
        }
    }
    public static List<File> getFilesRecursively(File file) {
        return getFilesRecursively(file, new ArrayList<>());
    }

    private static List<File> getFilesRecursively(File file, List<File> files) {
        if (file == null) return files;

        if (file.isDirectory()) {
            File[] childFile = Objects.requireNonNullElse(file.listFiles(), new File[0]);
            for (File child : childFile) {
                if (child.isDirectory()) getFilesRecursively(child, files);
                else files.add(child);
            }
        }
        return files;
    }

    public static String colorToHex(Paint paint) {
        if (paint instanceof Color color) {
            return colorToHex(color);
        }
        return null; // or some fallback value
    }
    public static String colorToHex(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }

    public static void enableDragStage(Stage stage, Node draggable) {
        double[] initialDimensions = {0, 0}; // X, Y
        AtomicBoolean isDragging = new AtomicBoolean(false);
        draggable.setOnMousePressed(event -> {
            // Check if Ctrl key is pressed and left mouse button is clicked
            if (event.isControlDown() && event.getButton() == MouseButton.PRIMARY) {
                isDragging.set(true);
                initialDimensions[0] = event.getScreenX() - stage.getX();
                initialDimensions[1] = event.getScreenY() - stage.getY();
            }
        });

        draggable.setOnMouseReleased(event -> {
            isDragging.set(false); // Disable dragging when mouse button is released
        });

        draggable.setOnMouseDragged(event -> {
            if (isDragging.get()) {
                // Calculate the stage's new position
                double newX = event.getScreenX() - initialDimensions[0];
                double newY = event.getScreenY() - initialDimensions[1];
                stage.setX(newX);
                stage.setY(newY);
            }
        });
    }

    @Deprecated
    public static void OnFileDropped(Node dropArea, Consumer<File> consumeFunction, Color dragOverColor, Color dragDropColor) {
        String dropAreaStyle = dropArea.getStyle();

        String d_overColor = colorToHex(dragOverColor);
        String d_dropColor = colorToHex(dragDropColor);
        String initialColor = AppManager.getStyleValue(dropAreaStyle, "-fx-background-color");

        // Set up drag and drop event handlers
        dropArea.setOnDragOver(event -> {
            if (event.getGestureSource() != dropArea && event.getDragboard().hasFiles()) {
                dropArea.setStyle("-fx-background-color: " + d_overColor);
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
            dropArea.setStyle("-fx-background-color: " + initialColor);
        });

        dropArea.setOnDragExited(event -> dropArea.setStyle("-fx-background-color: " + initialColor));

        dropArea.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasFiles()) {
                dropArea.setStyle("-fx-background-color: " + d_dropColor);
                success = true;
                dragboard.getFiles().forEach(consumeFunction);
            }

            event.setDropCompleted(success);
            event.consume();
            dropArea.setStyle("-fx-background-color: " + initialColor);
        });
    }

    public static String getStyleValue(String getStyle, String key) {
        Matcher matcher = Pattern.compile(Pattern.quote(key) + ":\\s*(\"[^\"]+\"|[^\"]+);").matcher(getStyle);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static @NotNull Class<?> getCallingClass() {
        try {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            StackTraceElement stackTraceElement = stackTraceElements[stackTraceElements.length - 1];

            String callingClazz = stackTraceElement.getClassName();
            return Class.forName(callingClazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static @NotNull Class<?> getCallingClass(int index) {
        try {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            StackTraceElement stackTraceElement = stackTraceElements[index];

            String callingClazz = stackTraceElement.getClassName();
            return Class.forName(callingClazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
