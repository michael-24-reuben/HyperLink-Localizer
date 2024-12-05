package fx.pisces.hyperlocal.utils.keyframe;

import javafx.animation.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StageTransition extends FXTransition {
    private final Stage stage;

    public StageTransition(Stage stage) {
        this.stage = stage;
    }
    public ScaleTransition scaleTransition(double toWidth, double toHeight, Duration duration) {
        // Use SimpleDoubleProperties for width and height
        SimpleDoubleProperty widthProperty = new SimpleDoubleProperty(stage.getWidth());
        SimpleDoubleProperty heightProperty = new SimpleDoubleProperty(stage.getHeight());

        // Create timeline to animate width and height of the stage
        Timeline sizeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                new KeyValue(widthProperty, stage.getWidth()),
                new KeyValue(heightProperty, stage.getHeight())),
            new KeyFrame(duration,
                new KeyValue(widthProperty, toWidth, Interpolator.LINEAR),
                new KeyValue(heightProperty, toHeight, Interpolator.LINEAR))
        );

        // Update the stage dimensions during the animation
        widthProperty.addListener((obs, oldVal, newVal) -> stage.setWidth(newVal.doubleValue()));
        heightProperty.addListener((obs, oldVal, newVal) -> stage.setHeight(newVal.doubleValue()));

        // Start the animation
        sizeTimeline.play();
        return null;
    }

    public void animateToLocation(double targetX, double targetY, Duration duration) {
        // Set up stage position properties
            SimpleDoubleProperty xPosition = new SimpleDoubleProperty(stage.getX());
            SimpleDoubleProperty yPosition = new SimpleDoubleProperty(stage.getY());
            xPosition.addListener((obs, oldVal, newVal) -> stage.setX(newVal.doubleValue()));
            yPosition.addListener((obs, oldVal, newVal) -> stage.setY(newVal.doubleValue()));

            // Create timeline for moving the stage
            Timeline moveTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(xPosition, stage.getX()),
                            new KeyValue(yPosition, stage.getY())),
                    new KeyFrame(duration,
                            new KeyValue(xPosition, targetX, Interpolator.LINEAR),
                            new KeyValue(yPosition, targetY, Interpolator.LINEAR))
            );

            moveTimeline.play();
    }
    public void setSizeWithTopShift(double targetWidth, double targetHeight, Duration duration) {
        // Use SimpleDoubleProperties for width, height, and Y-position
        SimpleDoubleProperty widthProperty = new SimpleDoubleProperty(stage.getWidth());
        SimpleDoubleProperty heightProperty = new SimpleDoubleProperty(stage.getHeight());
        SimpleDoubleProperty yPosition = new SimpleDoubleProperty(stage.getY());

        // Calculate the height difference
        double initialHeight = stage.getHeight();
        double heightDifference = targetHeight - initialHeight;

        // Calculate the new Y position (moving the top edge upwards)
        double targetY = stage.getY() - heightDifference;

        // Create timeline to animate width, height, and stage position
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                new KeyValue(widthProperty, stage.getWidth()),
                new KeyValue(heightProperty, stage.getHeight()),
                new KeyValue(yPosition, stage.getY())
            ),
            new KeyFrame(duration,
                new KeyValue(widthProperty, targetWidth, Interpolator.LINEAR),
                new KeyValue(heightProperty, targetHeight, Interpolator.LINEAR),
                new KeyValue(yPosition, targetY, Interpolator.LINEAR)
            )
        );

        // Update the stage width, height, and position during the animation
        widthProperty.addListener((obs, oldVal, newVal) -> stage.setWidth(newVal.doubleValue()));
        heightProperty.addListener((obs, oldVal, newVal) -> stage.setHeight(newVal.doubleValue()));
        yPosition.addListener((obs, oldVal, newVal) -> stage.setY(newVal.doubleValue()));

        // Start the animation
        timeline.play();
    }
}
