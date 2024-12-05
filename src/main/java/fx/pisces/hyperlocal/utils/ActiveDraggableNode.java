package fx.pisces.hyperlocal.utils;

import fx.pisces.hyperlocal.assets.graphics.stp_up_file.FilePaneController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.openqa.selenium.InvalidArgumentException;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ActiveDraggableNode {
    private static AtomicInteger MAX_CONCURRENT_NODES = new AtomicInteger(0);
    private static final ConcurrentLinkedQueue<Node> activeDragNodes = new ConcurrentLinkedQueue<>();

    public static boolean setMaxSlots(final int maxEvents) {
        assert maxEvents > 0;
        int activeSlotSize = activeDragNodes.size();

        // Ensures slot safety by preventing the max set events being less than active events
        if (!activeDragNodes.isEmpty() && activeSlotSize > maxEvents) {
            return false;
        }
        MAX_CONCURRENT_NODES = new AtomicInteger(maxEvents);
        return true;
    }

    public static int getActiveSlots() {
        return activeDragNodes.size();
    }

    public static int getMaxSlots() {
        return MAX_CONCURRENT_NODES.get();
    }

    public static boolean addActiveDragNode(final Node node) {
        return activeDragNodes.add(node);
    }

    public static boolean removeActiveDragNode(final Node node) {
        return activeDragNodes.remove(node);
    }

    public static Node getActiveDragNode(final Node node) {
        for (Node dragNode : activeDragNodes) {
            FilePaneController filePaneController = FilePaneController.from(dragNode);

            if (filePaneController.root().equals(node)) {
                return dragNode;
            }
        }
        new InvalidArgumentException("Node not found").printStackTrace();
        return null;
    }

    public static Node getActiveDragNode(final Pane container, final File file) {
        ObservableList<Node> containerChildren = container.getChildren();
        for (Node dragNode : containerChildren) {
            FilePaneController filePaneController = FilePaneController.from(dragNode);

            Object contentDetails = filePaneController.getContentDetails();
            if (contentDetails instanceof File fileDetails) {
                if (fileDetails.equals(file)) return dragNode;

            } else if (contentDetails instanceof Path pathDetails) {
                if (pathDetails.toFile().equals(file)) return dragNode;

            } else if (contentDetails instanceof String stringDetails && !stringDetails.isEmpty() && new File(stringDetails).exists()) {
                if (new File(stringDetails).equals(file)) return dragNode;

            } else
                if (contentDetails.toString().equals(file.toString())) return dragNode;
        }
        new InvalidArgumentException("Node not found").printStackTrace();
        return null;
    }

    public static boolean contains(final Node node) {
        return activeDragNodes.contains(node);
    }

    public static List<Node> getActiveNodes() {
        return new ArrayList<>(activeDragNodes);
    }

}
