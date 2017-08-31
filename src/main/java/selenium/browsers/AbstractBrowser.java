package selenium.browsers;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import selenium.exceptions.BrowserException;
import selenium.utils.OSUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Tsovak_Sahakyan.
 */
public class AbstractBrowser implements Browser {

    private final Supplier<WebDriver> builder;

    private final BrowserType type;

    private List<LogEntry> netLogs = new ArrayList<>();

    private List<LogEntry> consoleLogs = new ArrayList<>();

    private WebDriverActions actions;

    private WebDriver driver;

    private String id = "";

    private boolean started = false;

    private boolean closed = false;

    AbstractBrowser(Supplier<WebDriver> builder, BrowserType type) {
        this.builder = builder;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Browser setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public Browser start() {
        this.driver = builder.get();
        started = true;
        closed = false;
        //Browsers.addBrowser(this);
        return this;
    }

    @Override
    public WebDriver getDriver() {
        if (!isStarted()) {
            throw new BrowserException("The browser is not running");
        }
        return this.driver;
    }

    @Override
    public BrowserType getType() {
        return type;
    }

    @Override
    public void close() {
        if (isStarted()) {
            Browsers.getLogMap().put(getId(), Pair.of(getNetworkLog(), getConsoleLog()));
            driver.quit();
        }
        driver = null;
        started = false;
        closed = true;
        Browsers.removeBrowser(this);
    }

    @Override
    public Browser restart() {
        close();
        start();
        return this;
    }

    @Override
    public Browser get(String url) {
        driver.get(url);
        return this;
    }

    @Override
    public Browser nextWindow() {
        final String currentWindow = driver.getWindowHandle();
        final Set<String> windows = driver.getWindowHandles();
        if (windows.size() > 1) {
            windows.remove(currentWindow);
            driver.switchTo().window(windows.iterator().next());
        }
        return this;
    }

    @Override
    public Browser closeWindow() {
        final Set<String> windows = driver.getWindowHandles();
        if (windows.size() < 2) {
            driver.close();
        } else {
            close();
        }
        return this;
    }

    @Override
    public List<LogEntry> getConsoleLog() {
        if (Browsers.getBrowser().getType() != BrowserType.FIREFOX) {
            consoleLogs.addAll(getDriver().manage().logs().get(LogType.BROWSER).getAll());
        }
        return consoleLogs;
    }

    @Override
    public NetworkLog getNetworkLog() {
        if (Browsers.getBrowser().getType() != BrowserType.FIREFOX) {
            netLogs.addAll(getDriver().manage().logs().get(LogType.PERFORMANCE).getAll());
            return new NetworkLog(netLogs);
        } else {
            return new NetworkLog(Collections.emptyList());
        }
    }

    @Override
    public void clearLogs() {
        consoleLogs.clear();
        netLogs.clear();
    }

    @Override
    public Object getVariable(String vName) {
        return executeScript("return " + vName);
    }

    @Override
    public WebDriverActions actions() {
        if (actions == null) {
            this.actions = new WebDriverActions(driver);
        }
        return this.actions;
    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public String toString() {
        return "AbstractBrowser{" +
               "type=" + type +
               ", id='" + id + '\'' +
               ", started=" + started +
               ", closed=" + closed +
               '}';
    }
}
