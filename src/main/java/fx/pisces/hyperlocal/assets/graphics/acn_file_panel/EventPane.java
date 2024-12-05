package fx.pisces.hyperlocal.assets.graphics.acn_file_panel;

import fx.pisces.hyperlocal.assets.graphics.FXObject;
import fx.pisces.hyperlocal.assets.graphics.FXObjectController;

public class EventPane extends FXObject {
    public EventPane() {
        super("tip_prev_file.fxml");
    }

    @Override
    public EventPaneController getController() {
        return (EventPaneController) fxmlController;
    }

}
