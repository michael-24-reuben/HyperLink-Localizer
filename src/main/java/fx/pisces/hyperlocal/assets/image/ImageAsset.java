package fx.pisces.hyperlocal.assets.image;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ImageAsset {
    private final URI uri_file;
    public static final Image CSV = new ImageAsset("icons/csv.png").getImage();
    public static final Image DOCX = new ImageAsset("icons/docx.png").getImage();
    public static final Image FILE = new ImageAsset("icons/file.png").getImage();
    public static final Image HTML = new ImageAsset("icons/html.png").getImage();
    public static final Image JS = new ImageAsset("icons/js.png").getImage();
    public static final Image PHP = new ImageAsset("icons/php.png").getImage();

    public ImageAsset(String name) {
        try {
            URL resource = this.getClass().getResource( name);
            if (resource == null) {
                throw new IllegalArgumentException("Resource not found: " + name);
            }

            uri_file = resource.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image OF(@NotNull String name) {
        return new ImageAsset(name).getImage();
    }

    public Image getImage() {
        return new Image(uri_file.toString());
    }
}
//