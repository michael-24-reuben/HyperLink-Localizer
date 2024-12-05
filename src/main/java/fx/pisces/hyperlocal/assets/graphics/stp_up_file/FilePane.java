package fx.pisces.hyperlocal.assets.graphics.stp_up_file;

import fx.pisces.hyperlocal.assets.graphics.FXObject;

public class FilePane extends FXObject {
    private FilePane(final String name) {
        super(name);
    }

    public FilePane() {
        this("main.fxml");
    }

    public static class AsPopup extends FXObject {
        AsPopup() {
            super("asPopup.fxml");
        }

        public PopupFileController getController() {
            return (PopupFileController) fxmlController;
        }
    }

    public static AsPopup asPopup() {
        return new AsPopup();
    }

    public FilePaneController getController() {
        return (FilePaneController) fxmlController;
    }
}
