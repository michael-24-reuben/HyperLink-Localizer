package fx.pisces.hyperlocal.assets.graphics.acn_file_panel;

import fx.pisces.hyperlocal.assets.graphics.FXObjectController;
import fx.pisces.hyperlocal.assets.image.ImageAsset;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class EventPaneController extends FXObjectController {
    private final List<String> labelsText = new ArrayList<>();

    @FXML
    public TitledPane tip_prev_file;

    @FXML
    public ImageView img_up_icon;

    @FXML
    public VBox vbx_href_container;

    public void setTitle(String title) {
        tip_prev_file.setText(title);
    }

    public void setIcon(ImageAsset icon) {
        this.img_up_icon.setImage(icon.getImage());
    }

    public VBox container() {
        return vbx_href_container;
    }

    public void addLabel(String text) {
        Label label = new Label(text);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);
        label.setPadding(new Insets(1.0, 2.0, 1.0, 2.0));
        label.setFont(new Font("Bell MT", 14.2));
        label.setTextFill(Paint.valueOf("#434b5a"));

        vbx_href_container.getChildren().add(label);
    }

    public List<String> getLabelsText() {
        vbx_href_container.getChildren().forEach(node -> {
            if (node instanceof Label label) {
                labelsText.add(label.getText());
            }
        });
        return labelsText;
    }

    @Override
    public TitledPane root() {
        return tip_prev_file;
    }
}
