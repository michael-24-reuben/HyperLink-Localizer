package fx.pisces.hyperlocal.utils.file.log;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.io.*;
import java.util.List;

public class TempFileManager {
    private static final URL LOG_URL = TempFileManager.class.getResource("tempFileLog.txt");

    // Save the file path to the log
    public static void logTempFile(Path filePath) throws IOException, URISyntaxException {
        assert LOG_URL != null;
        Path LOG_PATH = Paths.get(LOG_URL.toURI());

        Files.writeString(LOG_PATH, filePath.toString() + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    // Read all logged paths
    public static List<String> getLoggedPaths() throws IOException {
        assert LOG_URL != null;
        Path LOG_PATH = Paths.get(LOG_URL.getPath());
        if (!Files.exists(LOG_PATH)) {
            return List.of(); // Return empty if no log exists
        }
        return Files.readAllLines(LOG_PATH);
    }

    // Clear the log
    public static void clearLog() throws IOException {
        assert LOG_URL != null;
        Path LOG_PATH = Paths.get(LOG_URL.getPath());
        Files.deleteIfExists(LOG_PATH);
    }
}
//