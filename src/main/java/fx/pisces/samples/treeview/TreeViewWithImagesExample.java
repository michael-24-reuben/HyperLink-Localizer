package fx.pisces.samples.treeview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Random;

public class TreeViewWithImagesExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load a sample image (replace with your own path or URL)
        Image parentImage = new Image("file:parent.png");
        Image childImage = new Image("file:child.png");

        // Root node
        TreeItem<String> root = createTreeItem("Root", parentImage);
        root.setExpanded(true);

        // Random object for generating child counts
        Random random = new Random();

        // Add 5 parents with random children
        for (int i = 1; i <= 5; i++) {
            TreeItem<String> parent = createTreeItem("Parent " + i, parentImage);

            // Generate between 3 and 7 children for this parent
            int childCount = 3 + random.nextInt(5); // 3 to 7
            for (int j = 1; j <= childCount; j++) {
                TreeItem<String> child = createTreeItem("Child " + j + " of Parent " + i, childImage);
                parent.getChildren().add(child);
            }

            // Add parent to root
            root.getChildren().add(parent);
        }

        // Create TreeView
        TreeView<String> treeView = new TreeView<>(root);

        // Layout and scene setup
        StackPane rootPane = new StackPane(treeView);
        Scene scene = new Scene(rootPane, 400, 300);

        primaryStage.setTitle("TreeView with Images Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to create a TreeItem with an image
    private TreeItem<String> createTreeItem(String text, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        return new TreeItem<>(text, imageView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
