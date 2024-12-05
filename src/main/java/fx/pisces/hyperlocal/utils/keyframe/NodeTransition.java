package fx.pisces.hyperlocal.utils.keyframe;

import javafx.animation.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.util.Duration;

public class NodeTransition extends FXTransition {
    private final Node node;
    private final double nodeWidth;
    private final double nodeHeight;

    public NodeTransition(Node node) {
        this.node = node;
        nodeWidth = node.getLayoutBounds().getWidth();
        nodeHeight = node.getLayoutBounds().getHeight();
    }

    public ScaleTransition scaleTransition(double toWidth, double toHeight, Duration duration) {
        toWidth = toWidth / node.getLayoutBounds().getWidth();
        toHeight = toHeight / node.getLayoutBounds().getHeight();

        // Create a ScaleTransition for size animation
        ScaleTransition scaleTransition = new ScaleTransition(duration, node);
        scaleTransition.setFromX(1.0);
        scaleTransition.setToX(toWidth); // Scale width by 1.5
        scaleTransition.setFromY(1.0);
        scaleTransition.setToY(toHeight); // Scale height by 1.5
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);
        return scaleTransition;
    }
    public ScaleTransition scaleTransitionHeight(double toHeight, Duration duration) {
        toHeight = toHeight / node.getLayoutBounds().getHeight();

        // Create a ScaleTransition for size animation
        ScaleTransition scaleTransition = new ScaleTransition(duration, node);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToY(toHeight); // Scale height by 1.5
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);
        return scaleTransition;
    }
    public ScaleTransition scaleTransitionWidth(double toWidth, Duration duration) {
        toWidth = toWidth / node.getLayoutBounds().getWidth();

        // Create a ScaleTransition for size animation
        ScaleTransition scaleTransition = new ScaleTransition(duration, node);
        scaleTransition.setFromX(1.0);
        scaleTransition.setToX(toWidth); // Scale width by 1.5
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);
        return scaleTransition;
    }

    public Timeline sizeTransition(double toWidth, double toHeight, Duration duration) {
        // Use SimpleDoubleProperties for width and height
        SimpleDoubleProperty widthProperty = new SimpleDoubleProperty(nodeWidth);
        SimpleDoubleProperty heightProperty = new SimpleDoubleProperty(nodeHeight);

        // Create timeline to animate width and height of the stage
        Timeline sizeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                new KeyValue(widthProperty, nodeWidth),
                new KeyValue(heightProperty, nodeHeight)),
            new KeyFrame(duration,
                new KeyValue(widthProperty, toWidth, Interpolator.LINEAR),
                new KeyValue(heightProperty, toHeight, Interpolator.LINEAR))
        );

        // Update the stage dimensions during the animation
        widthProperty.addListener((obs, oldVal, newVal) -> node.prefWidth(newVal.doubleValue()));
        heightProperty.addListener((obs, oldVal, newVal) -> node.prefHeight(newVal.doubleValue()));

        return sizeTimeline;
    }
    public Timeline sizeTransitionHeight(double toHeight, Duration duration) {
        SimpleDoubleProperty heightProperty = new SimpleDoubleProperty(nodeHeight);

        // Create timeline to animate height of the stage
        Timeline sizeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0), new KeyValue(heightProperty, nodeHeight)),
            new KeyFrame(duration, new KeyValue(heightProperty, toHeight, Interpolator.LINEAR))
        );

        // Update the stage dimensions during the animation
        heightProperty.addListener((obs, oldVal, newVal) -> node.prefHeight(newVal.doubleValue()));

        return sizeTimeline;
    }
    public Timeline sizeTransitionWidth(double toWidth, Duration duration) {
        SimpleDoubleProperty widthProperty = new SimpleDoubleProperty(nodeWidth);

        // Create timeline to animate width of the stage
        Timeline sizeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(widthProperty, nodeWidth)),
                new KeyFrame(duration, new KeyValue(widthProperty, toWidth, Interpolator.LINEAR))
        );

        // Update the stage dimensions during the animation
        widthProperty.addListener((obs, oldVal, newVal) -> node.prefWidth(newVal.doubleValue()));

        return sizeTimeline;
    }

    public FadeTransition fadeOut(Duration duration) {
        return fadeOut(1.0, 0.0, false, duration);
    }
    public FadeTransition fadeOut(boolean setExistent, Duration duration) {
        return fadeOut(1.0, 0.0, setExistent, duration);
    }
    public FadeTransition fadeOut(double fromValue, double toValue, boolean setExistent, Duration duration) {
        // Create a FadeTransition for opacity animation
        FadeTransition fadeTransition = new FadeTransition(duration, node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setAutoReverse(true);

        // Set visibility to false when the animation finishes
        fadeTransition.setOnFinished(event -> {
            node.setVisible(setExistent);
            node.setManaged(setExistent);
        });
//        fadeTransition.play();
        return fadeTransition;
    }

    public FadeTransition fadeIn(Duration duration) {
        return fadeIn(1.0, 1.0, false, duration);
    }
    public FadeTransition fadeIn(boolean setExistent, Duration duration) {
        return fadeIn(1.0, 1.0, setExistent, duration);
    }
    public FadeTransition fadeIn(double fromValue, double toValue, boolean setExistent, Duration duration) {
        // Set visibility to true before animation starts
        node.setVisible(setExistent);
        node.setManaged(setExistent);

        // Create a FadeTransition for opacity animation
        FadeTransition fadeTransition = new FadeTransition(duration, node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setAutoReverse(true);
//        fadeTransition.play();
        return fadeTransition;
    }

}
