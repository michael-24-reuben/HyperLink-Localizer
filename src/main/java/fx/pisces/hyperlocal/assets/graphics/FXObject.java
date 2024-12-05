package fx.pisces.hyperlocal.assets.graphics;

import javafx.fxml.FXMLLoader;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.InvalidArgumentException;

import java.io.IOException;
import java.net.URL;

public abstract class FXObject extends FXComponent {
    protected Object fxmlController;
    public FXObject(@NotNull String resourceFile) {
        super(FXMLLoader(resourceFile));
        try {
            fxmlLoader.load();
            fxmlController = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static FXMLLoader FXMLLoader(@NotNull String resourceFile) {
        Class<?> callingClass = getCallingClass();

        URL resource = callingClass.getResource(resourceFile);
        if (resource == null) {
            throw new InvalidArgumentException("FXML file not found at: " + resourceFile);
        }
        return new FXMLLoader(resource);
    }

    public static @NotNull Class<?> getCallingClass() {
        Class<?> callingClass = FXObject.class;
        Class<?> tempCallingClass;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        int index = 0;
        do {
            try {
                StackTraceElement stackTraceElement = stackTraceElements[index++];
                String tempCallingClazz = stackTraceElement.getClassName();
                tempCallingClass = Class.forName(tempCallingClazz);

                if (FXObject.class.isAssignableFrom(tempCallingClass)) {
                    callingClass = tempCallingClass;
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            // Stops at `CompositeEventHandler` because classes beyond that are external libraries or at last index
        } while (!tempCallingClass.getName().equals("com.sun.javafx.event.CompositeEventHandler") && index < stackTraceElements.length);
        return callingClass;
    }

    public abstract Object getController();
}
