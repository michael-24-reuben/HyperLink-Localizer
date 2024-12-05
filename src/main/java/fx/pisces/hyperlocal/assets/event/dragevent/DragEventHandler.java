package fx.pisces.hyperlocal.assets.event.dragevent;

import fx.pisces.hyperlocal.assets.event.dragevent.bookings.EventReservations;
import fx.pisces.hyperlocal.utils.object.NodeUserData;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;

public class DragEventHandler /*implements EventHandler<DragEvent>, DragEventBuilder*/ {}/*{
    private EventHandler<DragEvent> onDragEnteredTarget;
    private EventHandler<DragEvent> onDragEntered;
    private EventHandler<DragEvent> onDragExitedTarget;
    private EventHandler<DragEvent> onDragExited;
    private EventHandler<DragEvent> onDragOver;
    private EventHandler<DragEvent> onDragDropped;
    private EventHandler<DragEvent> onDragDone;

    public static DragEventBuilder scheduleEvent() {
        return new DragEventHandler();
    }

    @Override
    public ActiveDragState onDragEntered(final Node node, EventHandler<DragEvent> function) {
        node.setOnDragEntered(event -> {
            handleEvent(event, function, onDragEntered);
        });
        return this;
    }

    @Override
    public ActiveDragState onDragEnteredTarget(final Node node, EventHandler<DragEvent> function) {
        node.setOnDragEntered(event -> {
            if (event.getEventType() == DragEvent.DRAG_ENTERED_TARGET) {
                handleEvent(event, function, onDragEnteredTarget);
            }
        });
        return this;
    }

    @Override
    public ActiveDragState onDragOver(final Node node, EventHandler<DragEvent> function) {
        node.setOnDragOver(event -> {
            handleEvent(event, function, onDragOver);
        });
        return this;
    }

    @Override
    public ActiveDragState onDragDropped(final Node node, EventHandler<DragEvent> function) {
        node.setOnDragDropped(event -> {
            handleEvent(event, function, onDragDropped);
        });
        return this;
    }

    @Override
    public ActiveDragState onDragExited(final Node node, EventHandler<DragEvent> function) {
        node.setOnDragExited(event -> {
            handleEvent(event, function, onDragExited);
        });
        return this;
    }

    @Override
    public ActiveDragState onDragExitedTarget(final Node node, EventHandler<DragEvent> function) {
        node.setOnDragExited(event -> {
            if (event.getEventType() == DragEvent.DRAG_EXITED_TARGET) {
                handleEvent(event, function, onDragExitedTarget);
            }
        });
        return this;
    }

    @Override
    public void onDragDone(final Node node, EventHandler<DragEvent> function) {
        node.setOnDragDone(event -> {
            handleEvent(event, function, onDragDone);
        });
    }

    private void handleEvent(DragEvent event, EventHandler<DragEvent> function, EventHandler<DragEvent> scheduled) {
        System.out.println("event = " + event);
        System.out.println("function.toString() = " + function.toString());
        System.out.println("scheduled.toString() = " + scheduled.toString());
        function.handle(event);
        scheduled.handle(event);
    }

    @Override
    public void handle(DragEvent event) {
        switch (event.getEventType().getName()) {
            case "DRAG_ENTERED":
                if (onDragEntered != null) {
                    onDragEntered.handle(event);
                }
                break;
            case "DRAG_ENTERED_TARGET":
                if (onDragEnteredTarget != null) {
                    onDragEnteredTarget.handle(event);
                }
                break;
            case "DRAG_EXITED":
                if (onDragExited != null) {
                    onDragExited.handle(event);
                }
                break;
            case "DRAG_EXITED_TARGET":
                if (onDragExitedTarget != null) {
                    onDragExitedTarget.handle(event);
                }
                break;
            case "DRAG_OVER":
                if (onDragOver != null) {
                    onDragOver.handle(event);
                }
                break;
            case "DRAG_DROPPED":
                if (onDragDropped != null) {
                    onDragDropped.handle(event);
                }
                event.setDropCompleted(true);
                break;
            case "DRAG_DONE":
                if (onDragDone != null) {
                    onDragDone.handle(event);
                }
                break;
            default:
                System.out.println("Unhandled event: " + event.getEventType());
        }

    }

    @Override
    public EventReservations reserveOnDragEntered(EventHandler<DragEvent> function) {
        onDragEntered = function;
        return this;
    }

    public EventReservations reserveOnDragEnteredTarget(EventHandler<DragEvent> function) {
        onDragEnteredTarget = function;
        return this;
    }

    @Override
    public EventReservations reserveOnDragExited(EventHandler<DragEvent> function) {
        onDragExited = function;
        return this;
    }

    @Override
    public EventReservations reserveOnDragExitedTarget(EventHandler<DragEvent> function) {
        onDragExitedTarget = function;
        return this;
    }

    @Override
    public EventReservations reserveOnDragOver(EventHandler<DragEvent> function) {
        onDragOver = function;
        return this;
    }

    @Override
    public EventReservations reserveOnDragDropped(EventHandler<DragEvent> function) {
        onDragDropped = function;
        return this;
    }

    @Override
    public EventReservations reserveOnDragDone(EventHandler<DragEvent> function) {
        onDragDone = function;
        return this;
    }

    @Override
    public ActiveDragState build() {
        return this;
    }
}*/
