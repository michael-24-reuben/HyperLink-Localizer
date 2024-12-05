package fx.pisces.selenium.driver;

import fx.pisces.hyperlocal.utils.props.app.ApplicationProperties;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class CanvasDriver implements WebDriver, JavascriptExecutor {
    private final WebDriver driver;
    private final JavascriptExecutor jse;
    private final ApplicationProperties properties;

    public CanvasDriver() throws IOException {
        this(new String[] {});
    }

    public CanvasDriver(String[] argOptions) throws IOException {
        this(argOptions, "Profile 1");
    }

    public CanvasDriver(String[] argOptions, String profileDir) throws IOException {
        super();
        properties = new ApplicationProperties();

        /* [BLOCK] New instance block. Launching Microsoft-edge browser */
        // Set the path to the EdgeDriver executable
        System.setProperty("webdriver.edge.driver", properties.computeProperty("webdriver.edge.driver"));

        // Set Edge options to use an existing profile
        driver = getWebDriver(profileDir, argOptions);
        jse = (JavascriptExecutor) driver;
    }

    public EdgeOptions addArguments(String[] args) {
        EdgeOptions options = new EdgeOptions();
        if (args == null) return options;

        for (String arg : args) {
            options.addArguments(arg);
        }
        return options;
    }

    private WebDriver getWebDriver(String profile, String[] argOptions) throws IOException {
        EdgeOptions options = addArguments(argOptions);
        options.addArguments("user-data-dir=" + properties.computeProperty("user-data-dir"));

        // Initialize the EdgeDriver with options
        try {
            options.addArguments("profile-directory=" + profile);
            return new EdgeDriver(options);
        } catch (Exception e) {
            // Kill all Edge processes before starting
            Runtime.getRuntime().exec("taskkill /F /IM msedge.exe");

            options.addArguments("profile-directory=" + profile); /*"Default" for the daily web profile*/
            return new EdgeDriver(options);
        }
    }

    @Override
    public void get(@NotNull String url) {
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public @NotNull List<WebElement> findElements(@NotNull By by) {
        return driver.findElements(by);
    }

    @Override
    public @NotNull WebElement findElement(@NotNull By by) {
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public @NotNull Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public @NotNull String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public @NotNull TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public @NotNull Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public @NotNull Options manage() {
        return driver.manage();
    }

    @Override
    public Object executeScript(@NotNull String script, Object... args) {
        return jse.executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(@NotNull String script, Object... args) {
        return jse.executeAsyncScript(script, args);
    }

}
