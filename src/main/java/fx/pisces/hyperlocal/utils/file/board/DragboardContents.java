package fx.pisces.hyperlocal.utils.file.board;

import fx.pisces.hyperlocal.utils.file.log.TempFileManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DragboardContents {
    private static final ConcurrentLinkedQueue<BoardContent> boardContents = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<File> activeFiles = new ConcurrentLinkedQueue<>();

    public static class BoardContent {
        final ConcurrentHashMap<String, Object> properties = new ConcurrentHashMap<>();
        private final File lcl_file;
        private final File tmp_file;

        BoardContent(File lcl_file, File tmp_file) {
            this.lcl_file = lcl_file;
            this.tmp_file = tmp_file;
        }

        public File getLocalFile() {
            return lcl_file;
        }

        public File getTempFile() {
            return tmp_file;
        }

        public Object getProperty(String key) {
            return this.properties.get(key);
        }

        public String toString() {
            return Map.ofEntries(
                    Map.entry("local-file", lcl_file),
                    Map.entry("temp-file", tmp_file),
                    Map.entry("properties", properties)
            ).toString();
        }
    }

    public static boolean containsContent(File file) {
        return activeFiles.contains(file);
    }

    public static BoardContent addContent(File file) {
        return addContent(file, null, null);
    }

    public static BoardContent addContent(File file, String key, Object value) {
        return addContent(file, (key == null) ? new HashMap<>() : Map.of(key, value));
    }

    public static BoardContent addContent(File file, @NotNull Map<String, Object> properties) {
        if (file == null) return null;

        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        dotIndex = dotIndex == -1 ? fileName.length() : dotIndex + 1;
        String fileExtension = fileName.substring(dotIndex);
        fileName = fileName.substring(0, dotIndex);

        if (!activeFiles.add(file)) return null;
        try {
            File tempFile = File.createTempFile(fileExtension + "-" + fileName, null);
            TempFileManager.logTempFile(tempFile.toPath());
            tempFile.deleteOnExit();

            BoardContent boardContent = new BoardContent(file, tempFile);
            boardContent.properties.putAll(properties);
            if (boardContents.add(boardContent)) return boardContent;

        } catch (IOException | URISyntaxException e) {
            activeFiles.remove(file);
            e.printStackTrace();
        }
        return null;
    }

    public static boolean removeContent(File file) {
        activeFiles.remove(file);
        for (BoardContent boardContent : boardContents) {
            if (boardContent.getLocalFile().equals(file)) {
                boardContents.remove(boardContent);
                return true;
            }
        }
        return false;
    }

    public static BoardContent getContent(File file) {
        for (BoardContent boardContent : boardContents) {
            if (boardContent.getLocalFile().equals(file)) {
                return boardContent;
            }
        }
        throw new NullPointerException("Null value produced ");
    }

    public static File getContent(File file, File defaultFile) {
        for (BoardContent boardContent : boardContents) {
            if (boardContent.getLocalFile().equals(file)) {
                return boardContent.getLocalFile();
            }
        }
        return defaultFile;
    }

    public static void clear() {
        activeFiles.clear();
        boardContents.clear();
    }

    public static void forEach(Consumer<BoardContent> consumer) {
        boardContents.forEach(consumer);
    }

    public static void forEach(BiConsumer<File, File> consumer) {
        boardContents.forEach(content -> consumer.accept(content.getLocalFile(), content.getTempFile()));
    }

}
