package fx.pisces.hyperlocal;

import fx.pisces.hyperlocal.assets.graphics.FXComponent;
import fx.pisces.hyperlocal.assets.graphics.stp_up_file.FilePane;
import fx.pisces.hyperlocal.assets.graphics.stp_up_file.FilePaneController;
import fx.pisces.hyperlocal.assets.image.ImageAsset;
import fx.pisces.hyperlocal.utils.ActiveDraggableNode;
import fx.pisces.hyperlocal.utils.AppManager;
import fx.pisces.hyperlocal.utils.file.board.DragboardContents;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class HyperController {
    @FXML
    public VBox root;

    // [Root-Left] --------------------------------------------------------------------------------
    @FXML
    public SplitPane spp_content;

    @FXML
    public ScrollPane scp_content_left;
    @FXML
    public Accordion acn_file_panel;

    // [Root-Center] --------------------------------------------------------------------------------
    @FXML
    public GridPane grp_content_center;

    @FXML
    public ScrollPane scp_upload_pane;


    @FXML
    public FlowPane flp_upload_pane;

    @FXML
    public ProgressBar prb_progress_bar;

    @FXML
    public GridPane grp_upload;
    @FXML
    public Label lbl_upload_intext;
    @FXML
    public GridPane grp_download;
    @FXML
    public Label lbl_download_intext;

    // [Root-Right] --------------------------------------------------------------------------------
    @FXML
    public ScrollPane scp_content_right;

    public record NodeDimension(Node item, double width, double height) {
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            double minWidth = calcCenterMinWidth() + 6;
            double minHeight = calcCenterMinHeight();
            grp_content_center.setMinWidth(minWidth);
            grp_content_center.setMinHeight(minHeight);
            root.setMinWidth(minWidth + 6);
            root.setMinHeight(minHeight + 6);

            double minCollapsibleWidth = (1 * grp_content_center.getMinWidth()) + grp_content_center.getMinWidth();
            grp_content_center.widthProperty().addListener((observable, oldValue, newValue) -> {
                double newWidthValue = newValue.doubleValue();

                if (newWidthValue <= minCollapsibleWidth) {
                    lbl_upload_intext.setVisible(false);
                    lbl_upload_intext.setManaged(false);
                    lbl_download_intext.setVisible(false);
                    lbl_download_intext.setManaged(false);
                } else {
                    lbl_upload_intext.setVisible(true);// 135.28 + 5.6
                    lbl_upload_intext.setManaged(true);
                    lbl_download_intext.setVisible(true);
                    lbl_download_intext.setManaged(true);
                }
            });

            List<Map<String, Object>> nodeMeta = new ArrayList<>();
            insertNodeMeta(nodeMeta);

            {
                AtomicReference<Map<String, Object>> nextMetaNode = new AtomicReference<>(nodeMeta.getFirst());
                int[] currItemCount = new int[]{0, nodeMeta.size(), 0}; // [currIndex, ttlIndex, prevIndex]
                root.widthProperty().addListener((observable, oldValue, newValue) -> {
                    double newWidthValue = newValue.doubleValue();
                    Map<String, Object> metaNode = nextMetaNode.get();
                    ObservableList<Node> contentItems = spp_content.getItems();
                    double collapseAtCurrWidth = (double) metaNode.get("triggerWidth");
                    boolean rootWidthLTCurrCollapsible = newWidthValue <= collapseAtCurrWidth;
                    double collapseAtPrevWidth = (double) nodeMeta.get(currItemCount[2]).get("triggerWidth");
                    boolean rootWidthLTPrevCollapsible = newWidthValue <= collapseAtPrevWidth;

//                System.out.println("Node: " + currItemCount[0] + " / " + currItemCount[1] + " :: newWidth[" + newWidthValue + "], collapseWidth[" + collapseAtCurrWidth + "]");
                    if (rootWidthLTCurrCollapsible) {
                        for (int index = currItemCount[0]; index < currItemCount[1]; index++) {
                            metaNode = nodeMeta.get(index);
                            nextMetaNode.set(metaNode);
                            Node collapsibleNode = (Node) metaNode.get("node");
                            collapseAtCurrWidth = (double) metaNode.get("triggerWidth");
                            boolean isCollapsible = (boolean) metaNode.get("isCollapsible");
                            rootWidthLTCurrCollapsible = newWidthValue <= collapseAtCurrWidth;

                            if (isCollapsible && rootWidthLTCurrCollapsible) {
                                currItemCount[2] = currItemCount[0];
                                contentItems.remove(collapsibleNode);
                            }
                            currItemCount[0] = index;
                        }

                    } else if (!rootWidthLTPrevCollapsible && contentItems.size() < nodeMeta.size()) { // If root width is gt and does not contain all elements in it
                        for (int index = currItemCount[0]; index >= 0; index--) {
                            metaNode = nodeMeta.get(index);
                            nextMetaNode.set(metaNode);
                            Node collapsibleNode = (Node) metaNode.get("node");
                            collapseAtCurrWidth = (double) metaNode.get("triggerWidth");
                            boolean isCollapsible = (boolean) metaNode.get("isCollapsible");
                            int lbCollapsible = (int) metaNode.get("lbCollapsibles");
                            boolean rootWidthGTCollapsible = newWidthValue > collapseAtCurrWidth;

                            currItemCount[0] = index;
                            if (isCollapsible && rootWidthGTCollapsible && !contentItems.contains(collapsibleNode)) {
                                contentItems.add(index - lbCollapsible, collapsibleNode);
                            }
                        }
                    }
                });
            } // Listens to the width property and attempts to remove requested nodes from left to right when the width value passes the items min width
        });

//        UrlContentDownloader contentDownloader = new UrlContentDownloader();
        grp_download.setOnMouseClicked(event -> {
            DragboardContents.forEach((lclFile, tempFile) -> {
                Node activeDragNode = ActiveDraggableNode.getActiveDragNode(flp_upload_pane, lclFile);
                FilePaneController filePaneController = FilePaneController.from(activeDragNode);
                filePaneController.lbl_content_details.setTextFill(Color.rgb(132, 225, 98));

//                contentDownloader.processHTMLContent(lclFile, tempFile, new HashMap<>());
            });
        });
    }

    private void insertNodeMeta(List<Map<String, Object>> nodeMeta) {
        List<Double> itemsWidth = new ArrayList<>();
        spp_content.getItems().forEach(item -> {
            double width = item.getLayoutBounds().getWidth();
            itemsWidth.add(width);
        });
        double triggerWidth = itemsWidth.stream().mapToDouble(Double::doubleValue).sum();

        int collapsibles = 0;
        ObservableList<Node> contentItems = spp_content.getItems();
        List<Integer> collapsibleRegions = List.of(1); // Indicates not to collapse nodes of this offset
        for (int atIndex = 0; atIndex < contentItems.size(); atIndex++) {
            boolean isCollapsible = !collapsibleRegions.contains(atIndex); // Item is collapsible if it's offset is not in the list
            Node node = contentItems.get(atIndex);

            Map<String, Object> item = Map.of(
                    "node", node,
                    "isCollapsible", isCollapsible,
                    "triggerWidth", triggerWidth,
                    "lbCollapsibles", collapsibles
            );
            if (isCollapsible) collapsibles++;
            nodeMeta.add(item);

            if (atIndex == 0) continue;
            triggerWidth -= itemsWidth.get(atIndex);
        }
    }

    private double getMinContentAllowedWidth(SplitPane pane) {
        return pane.getItems().stream().mapToDouble(items -> items.getLayoutBounds().getWidth()).sum();
    }

    public void init(Stage primaryStage) {
        grp_upload.setOnMouseClicked(event -> {
            File fileChosen = AppManager.showFileChooser(primaryStage, null);
            ImportFileContents(fileChosen);
        });
        FXComponent.builder(scp_upload_pane).onFileDropped(this::ImportFileContents);
    }

    private void ImportFileContents(File file) {
        if (file == null || DragboardContents.containsContent(file)) return;

        List<File> recursiveFiles = new ArrayList<>();
        if (file.isDirectory()) {
            System.out.println("\033[1;3;36m[" + file + "]\033[0m");
            recursiveFiles.addAll(AppManager.getFilesRecursively(file));
        } else
            recursiveFiles.add(file);

        for (File rFile : recursiveFiles) {
            System.out.println("\t\033[3;36m" + rFile + "\033[0m");
            if (DragboardContents.containsContent(rFile)) continue;

            ProcessDroppedFiles(rFile);
        }
    }

    private double calcCenterMinWidth() {
        double node1Width = scp_upload_pane.getWidth();
        double node2Width = flp_upload_pane.getWidth();
        Insets node2Padding = flp_upload_pane.getPadding();
        double tgtNodeWidth = 135.28;
        return ((node1Width - node2Width)) + tgtNodeWidth + node2Padding.getLeft() + node2Padding.getRight() + 6;
    }

    private double calcCenterMinHeight() {
//        double node1Height = scp_upload_pane.getHeight();
        Insets node2Padding = flp_upload_pane.getPadding();
        return 128 + node2Padding.getTop() + node2Padding.getBottom() + 36.6;
    }

    private void ProcessDroppedFiles(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        dotIndex = dotIndex == -1 ? fileName.length() : dotIndex + 1;
        String fileExtension = fileName.substring(dotIndex);
        Image contentImage = switch (fileExtension) {
            case "html" -> ImageAsset.OF("html-file.png");
            case "css" -> ImageAsset.OF("css-file.png");
            case "php" -> ImageAsset.OF("php-file.png");
            case "txt" -> ImageAsset.OF("txt-file.png");
            case "pdf" -> ImageAsset.OF("pdf-file.png");
            default -> ImageAsset.OF("plain-file.png");
        };

        FilePaneController controller = new FilePane().getController();

        controller.setContentDetails(file);
        controller.setContentImage(contentImage);

        InitializeMouseEvents(file, controller);

        flp_upload_pane.getChildren().add(controller.root());
        DragboardContents.addContent(file, Map.of("node.root", controller.root()));

        controller.btn_content_remove.setOnAction(event -> RemoveNodePresence(controller));
    }

    private void InitializeMouseEvents(File file, FilePaneController controller) {
        ImageView imgContentImage = controller.img_content_image;

        imgContentImage.setOnMousePressed(event -> {
            imgContentImage.setCursor(Cursor.CLOSED_HAND);
        });
        imgContentImage.setOnMouseReleased(event -> {
            imgContentImage.setCursor(Cursor.OPEN_HAND);
        });

        imgContentImage.setOnDragDetected(event -> {
            Dragboard dragboard = controller.grp_content_pane.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            File localFile = Objects.requireNonNull(DragboardContents.getContent(file)).getLocalFile();

            // Attach file path or data to the dragboard
            content.putFiles(List.of(localFile));
            dragboard.setContent(content);

            Image contentImage = controller.getContentImage();
            URI uri = URI.create(contentImage.getUrl());
            Image image = new Image(uri.toString(), 55, 55, true, true);
            dragboard.setDragView(image, (double) 55 / 2, (double) 55 / 2);

            event.consume();
        });
    }

    public double[] showPopupAtCenter(Popup popup, Node owner, double offsetX, double offsetY) {
        if (owner != null) {
            Bounds bounds = owner.localToScreen(owner.getBoundsInLocal());
            if (bounds != null) {
                double centerX = (bounds.getMinX() + bounds.getWidth() / 2) + offsetX;
                double centerY = (bounds.getMinY() + bounds.getHeight() / 2) + offsetY;

                // Adjust popup position to center
                double popupWidth = popup.getContent().getFirst().prefWidth(-1); // Assumes first child has a prefWidth
                double popupHeight = popup.getContent().getFirst().prefHeight(-1); // Assumes first child has a prefHeight

                double targetX = centerX - popupWidth / 2;
                double targetY = centerY - popupHeight / 2;
                popup.show(owner, targetX, targetY);

                return new double[]{targetX, targetY};
            } else {
                System.out.println("Owner bounds are null!");
            }
        } else {
            System.out.println("Owner node is null!");
        }
        return null;
    }


    private static int getIndexOfDragged(ObservableList<Node> flpUploadPaneChildren, FilePaneController controller) {
        for (int childAt = 0; childAt < flpUploadPaneChildren.size(); childAt++) {
            Node flpUploadPaneChild = flpUploadPaneChildren.get(childAt);

            if (controller.root().equals(flpUploadPaneChild)) {
                return childAt;
            }
        }
        return -1;
    }

    private void RemoveNodePresence(FilePaneController controller) {
        flp_upload_pane.getChildren().remove(controller.root());
        Object contentDetails = controller.getContentDetails();
        File contentFile = null;

        if (contentDetails instanceof File fileDetails) {
            contentFile = fileDetails;
        } else if (contentDetails instanceof Path pathDetails) {
            contentFile = pathDetails.toFile();
        } else if (contentDetails instanceof String stringDetails && !stringDetails.isEmpty() && new File(stringDetails).exists()) {
            contentFile = new File(stringDetails);
        }
        DragboardContents.removeContent(contentFile);
    }

}