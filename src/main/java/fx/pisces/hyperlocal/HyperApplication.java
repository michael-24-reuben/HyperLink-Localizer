package fx.pisces.hyperlocal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HyperApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HyperApplication.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        SetUpStage(stage, fxmlLoader);

        stage.setScene(scene);

        stage.show();
    }

    public void SetUpStage(Stage stage, FXMLLoader fxmlLoader) {
        stage.setTitle("Hyper Localizer");

        HyperController hyperController = fxmlLoader.getController();
        hyperController.init(stage);

    }
}
