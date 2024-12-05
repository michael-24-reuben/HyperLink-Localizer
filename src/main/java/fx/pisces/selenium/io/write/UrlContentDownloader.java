package fx.pisces.selenium.io.write;

import fx.pisces.hyperlocal.utils.props.app.ApplicationProperties;
import fx.pisces.selenium.driver.CanvasDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlContentDownloader {
    /*public static void main(String[] args) throws IOException {
        String inputText = """
                Here is an example:
                <a href="https://example.com/assets/script.js"></a>
                <img src="https://example.com/images/logo.svg" />
                <link rel="icon" href="https://example.com/favicon.ico" />
                """;
        new UrlContentDownloader().processHTMLContent(inputText, "output");
    }*/
    CanvasDriver canvasDriver;
    ApplicationProperties appProps;

    public UrlContentDownloader() {
        String[] argOptions = {"--headless", "--disable-gpu"};
        try {
            canvasDriver = new CanvasDriver(null);
            appProps = new ApplicationProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        canvasDriver.get("https://cpwebassets.codepen.io/assets/common/stopExecutionOnTimeout-2c7831bb44f98c1391d6a4ffda0e1fd302503391ca806e7fcc7b9b87197aec26.js");
    }

    public void processHTMLContent(File sourceFile, File destinationFile, Map<String, String[]> fieldValues) {
        String fileContents = readFileContents(sourceFile);
        boolean containsKeyDomainName = fieldValues.containsKey("domain.name");

        // The file types to load
        String fileTypeLoad = appProps.computeProperty("file.type.load").replace(";", "|");
        String extensions = "(" + fileTypeLoad + ")";
        // Regex to match the desired URLs
        String regex = "(src|href)=\"https://([^\"]+\\." + extensions + ")\"";
        if (containsKeyDomainName) {
            regex = "(src|href)=\"(/[^\"]+\\." + extensions + ")\"";
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContents);

        StringBuilder updatedText = new StringBuilder();

        while (matcher.find()) {
            String ofDomain = "https://";
            if (containsKeyDomainName) {
                ofDomain = fieldValues.get("domain.name")[0];
            }
            String fullUrl = ofDomain + matcher.group(2);
            String localPath = matcher.group(2); // Path after the domain name
            localPath = localPath.substring(localPath.indexOf("/") + 1);

            // Download the file and save locally
            File savedFile = downloadAndSaveFile(fullUrl, localPath, sourceFile.getParent());
            // Replace the URL with the local path
            matcher.appendReplacement(updatedText, matcher.group(1) + "=\"" + localPath + "\"");
        }
        matcher.appendTail(updatedText);
        try {
            Files.writeString(destinationFile.toPath(), updatedText.toString(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n\t\033[1;33m ---[ Process complete ]---\033[0m \n");
    }

    public File downloadAndSaveFile(String fileUrl, String localPath, String baseDir) {
        try {
            // Create the local directory structure
            File localFile = new File(baseDir, localPath);
            localFile.getParentFile().mkdirs();
            if (!localFile.exists()) {
                localFile.createNewFile();
            }

            canvasDriver.get(fileUrl);

            String pageSource = localPath.matches(".+\\.(css|mjs|js)$") ?
                    getPageSource(localPath, false) :
                    getPageSource(localPath, true);
            if (pageSource.isBlank())
                System.err.println("Empty url page source contents");

            Files.writeString(localFile.toPath(), pageSource, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("\033[1;35m [Reading]: \033[0m" + fileUrl);
            System.out.println("\033[1;32m [Writing]: \033[0m" + localFile.getPath());
            System.out.println();

            return new File(localPath);

            // System.err.println("Failed to download: " + fileUrl + " (HTTP " + connection.getResponseCode() + ")");
        } catch (IOException e) {
            System.err.println("Error processing URL: " + fileUrl + " - " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private String getPageSource(String localPath, boolean scrapeHTML) { // `scrapeHTML` extracts full content while false extracts innerText
        if (localPath.matches(".+\\.js$") && !scrapeHTML) {
            try {
                WebElement bodyElement = canvasDriver.findElement(By.tagName("body"));
                return bodyElement.getText();
            } catch (Exception e) {
                return getPageSource(localPath, true);
            }
        }
        //if (localPath.matches("\\.(ico|svg)$")) {
        return canvasDriver.getPageSource();
    }

    private static String readFileContents(File file) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contentBuilder.toString();
    }

    public static String extractDomain(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost();
        } catch (URISyntaxException e) {
            System.err.println("Invalid URL: " + url);
            return null;
        }
    }
}
