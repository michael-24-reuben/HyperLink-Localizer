package fx.pisces.samples.treeview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Random;

public class TreeViewExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root node
        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);

        // Random object for generating child counts
        Random random = new Random();

        // Add 5 parents with random children
        for (int i = 1; i <= 5; i++) {
            TreeItem<String> parent = new TreeItem<>("Parent " + i);

            // Generate between 3 and 7 children for this parent
            int childCount = 3 + random.nextInt(5); // 3 to 7
            for (int j = 1; j <= childCount; j++) {
                TreeItem<String> child = new TreeItem<>("Child " + j + " of Parent " + i);
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

        primaryStage.setTitle("TreeView Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
