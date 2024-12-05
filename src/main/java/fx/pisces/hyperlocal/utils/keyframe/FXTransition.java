package fx.pisces.hyperlocal.utils.keyframe;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public abstract class FXTransition {
    public abstract ScaleTransition scaleTransition(double toWidth, double toHeight, Duration duration);
}
