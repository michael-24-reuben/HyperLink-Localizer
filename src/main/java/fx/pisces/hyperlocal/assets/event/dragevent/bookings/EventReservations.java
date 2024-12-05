package fx.pisces.hyperlocal.assets.event.dragevent.bookings;

import fx.pisces.hyperlocal.assets.event.dragevent.ActiveDragState;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;

/**
 * A builder interface to provide functionality for reserving and chaining event handlers for drag-and-drop events.
 * Each method allows pre-defining an action to be invoked before the main event handler for the corresponding drag event is executed.
 */
public interface EventReservations {

    /**
     * Reserves an action to be executed when the drag gesture enters a node.
     * This action is triggered prior to the default event handler for `onDragEntered`.
     * This method does not distinguish between different nodes that may trigger the event—
     * it only responds when the drag enters the node associated with the event handler.
     *
     * @param function The event handler to invoke before the primary `onDragEntered` handler is executed.
     * @return An instance of {@link EventReservations} for further chaining.
     */
    EventReservations reserveOnDragEntered(EventHandler<DragEvent> function);

    /**
     * Reserves an action to be executed when the drag gesture enters the target node.
     * This action is triggered prior to the default event handler for `onDragEnteredTarget`.
     * Unlike {@code reserveOnDragEntered}, this method specifically handles the event for the target node.
     *
     * @param function The event handler to invoke before the primary `onDragEnteredTarget` handler is executed.
     * @return An instance of {@link EventReservations} for further chaining.
     */
    EventReservations reserveOnDragEnteredTarget(EventHandler<DragEvent> function);

    /**
     * Reserves an action to be executed when the drag gesture exits a node.
     * This action is triggered prior to the default event handler for `onDragExited`.
     * This method does not distinguish between different nodes that may trigger the event—
     * it only responds when the drag exits the node associated with the event handler.
     *
     * @param function The event handler to invoke before the primary `onDragExited` handler is executed.
     * @return An instance of {@link EventReservations} for further chaining.
     */
    EventReservations reserveOnDragExited(EventHandler<DragEvent> function);

    /**
     * Reserves an action to be executed when the drag gesture exits the target node.
     * This action is triggered prior to the default event handler for `onDragExited`.
     * Unlike {@code reserveOnDragExited}, this method specifically handles the event for the target node.
     *
     * @param function The event handler to invoke before the primary `onDragExited` handler is executed.
     * @return An instance of {@link EventReservations} for further chaining.
     */
    EventReservations reserveOnDragExitedTarget(EventHandler<DragEvent> function);

    /**
     * Reserves an action to be executed when the drag gesture is over a node.
     * This reserved action is triggered prior to the default event handler defined for `onDragOver`.
     *
     * @param function The event handler to invoke before the primary `onDragOver` handler is executed.
     * @return An instance of {@link EventReservations} for further chaining.
     */
    EventReservations reserveOnDragOver(EventHandler<DragEvent> function);

    /**
     * Reserves an action to be executed when the dragged content is dropped onto a node.
     * This reserved action is triggered prior to the default event handler defined for `onDragDropped`.
     *
     * @param function The event handler to invoke before the primary `onDragDropped` handler is executed.
     * @return An instance of {@link EventReservations} for further chaining.
     */
    EventReservations reserveOnDragDropped(EventHandler<DragEvent> function);

    /**
     * Reserves an action to be executed when the drag-and-drop gesture is completed.
     * This reserved action is triggered prior to the default event handler defined for `onDragDone`.
     *
     * @param function The event handler to invoke before the primary `onDragDone` handler is executed.
     * @return An instance of {@link EventReservations} for further chaining.
     */
    EventReservations reserveOnDragDone(EventHandler<DragEvent> function);

    void handle(DragEvent event);

    ActiveDragState build();
}
