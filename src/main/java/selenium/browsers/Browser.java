package selenium.browsers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;

import java.io.Closeable;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Tsovak_Sahakyan.
 */
public interface Browser  extends Closeable {

    /**
     * Get the browser ID (browser_name + ordinal_number)
     */
    String getId();

    /**
     * Set Browser ID
     */
    Browser setId(String id);

    /**
     * Open the browser. At this point, WebDriver is created. Called first before using the browser
     */
    Browser start();

    /**
     * Get a copy of the driver on the basis of which the Browser works
     */
    WebDriver getDriver();

    /**
     * Get {@link BrowserType}
     */
    BrowserType getType();

    /**
     * Close the browser. Kill the driver. Releasing all resources
     */
    void close();

    /**
     * Restart the driver and relaunch the browser
     */
    Browser restart();

    /**
     * Go to URL
     */
    Browser get(String url);

    /**
     * Change the active window as follows.
     */
    Browser nextWindow();

    /**
     * Close the active window
     */
    Browser closeWindow();

    /**
     * Get JS console logs
     */
    List<LogEntry> getConsoleLog();

    NetworkLog getNetworkLog();

    void clearLogs();

    /**
     * Get the value of the global variable JS on the page
     */
    Object getVariable(String vName);

    default Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) getDriver()).executeScript(script, args);
    }

    default <T> T executeScript(String script) {
        return (T) ((JavascriptExecutor) getDriver()).executeScript(script);
    }

    default void setAttribute(WebElement element, String attrName, String value) {
        Browsers.getBrowser().executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                                            element, attrName, value);
    }

    /**
     * Get {@link WebDriverActions}
     */
    WebDriverActions actions();

    /**
     * Maximize window
     */
    default Browser maximize() {
        Browsers.getBrowser().getDriver().manage().window().maximize();
        return this;
    }
    /**
     * Is browser started
     *
     */
    boolean isStarted();

    /**
     * Is the browser closed
     */
    boolean isClosed();

    /**
     * Create {@link Action} without running
     */
    default Action newAction(Function<Actions, Actions> func) {
        return func.apply(new Actions(getDriver())).build();
    }

    /**
     * Refresh the page
     */
    default void refresh() {
        getDriver().navigate().refresh();
    }
}
