package fx.pisces.hyperlocal.assets.event.dragevent;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

public interface ActiveDragState {
    ActiveDragState onDragEntered(EventHandler<DragEvent> function);

    ActiveDragState onDragOver(EventHandler<DragEvent> function);

    ActiveDragState onDragDropped(EventHandler<DragEvent> function);

    ActiveDragState onDragExited(EventHandler<DragEvent> function);

    void onDragComplete(EventHandler<MouseEvent> function);

    default void onDragComplete(Node owner, EventHandler<MouseEvent> function) {}
}
