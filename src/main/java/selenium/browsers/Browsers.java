package selenium.browsers;


import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.logging.LogEntry;
import selenium.exceptions.BrowserException;
import selenium.reports.Reporter;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
@UtilityClass
public class Browsers{

    private final static BrowserFactory browserFactory = new BrowserFactory();



    volatile private static Browser activeBrowser = null;

    private static List<Browser> browserList = new CopyOnWriteArrayList<>();

    private static AtomicInteger idCounter = new AtomicInteger();

    volatile private String lastId;

    @Getter
    private HashMap<String, Pair<NetworkLog, List<LogEntry>>> logMap = new HashMap<>();


    //METHODS
    public Pair<NetworkLog, List<LogEntry>> getLastLog() {
        if (activeBrowser != null) {
            return Pair.of(activeBrowser.getNetworkLog(), activeBrowser.getConsoleLog());
        }
        if (logMap.isEmpty()) {
            return null;
        }
        return logMap.get(lastId);
    }

    public static String addBrowser(Browser browser) {
        lastId = generateId(browser.getType().toString());
        browserList.add(browser.setId(lastId));
        activeBrowser = browser;
        return lastId;
    }

    public static String removeBrowser(Browser browser) {
        browserList.remove(browser);
        if (!browserList.isEmpty()) {
            activeBrowser = browserList.get(0);
        } else {
            activeBrowser = null;
        }

        return browser.getId();
    }

    public static Browser newBrowser(BrowserType type) {
        final Browser result = fetchBrowser(type);
        addBrowser(result);
        log.info("The browser is initialized : " + result.getId());
        return result;
    }

    public static boolean hasBrowser() {
        checkBrowsers();
        return activeBrowser != null;
    }

    public static Browser getBrowser() {
        return activeBrowser;
    }

    public static void checkBrowsers() {
        browserList.stream().forEach(item -> {
            if (item.isClosed()) {
                removeBrowser(item);
            }
        });
    }

    public static void stopAllBrowsers() {
        browserList.forEach(Browser::close);
    }

    //PRIVATE
    private static String generateId(String prefix) {
        return prefix + "-" + String.valueOf(idCounter.getAndIncrement());
    }

    private static Browser fetchBrowser(BrowserType type) {
        switch (type) {
            case IE:
                return browserFactory.newIeBrowser();
            case CHROME:
                return browserFactory.newChromeBrowser();
            case FIREFOX:
                return browserFactory.newFirefoxBrowser();
            case SAFARI:
                return browserFactory.newSafariBrowser();
            case EDGE:
                return browserFactory.newMicrosofatEdge();
            case OTHER:
                return browserFactory.newSafariBrowser();
            default:
                throw new BrowserException("Unsupported browser type : " + type.toString());
        }
    }
}
