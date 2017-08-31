package selenium.browsers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import selenium.configuration.Config;
import selenium.exceptions.BrowserException;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class BrowserFactory{

    public Browser newIeBrowser() {
        if (Config.USING_SELENIUM_HUB) {
            return new AbstractBrowser(() -> createRemoteWebDriver(BrowserConfigs.newIECapabilities()), BrowserType.IE);
        }
        configDriverPath(BrowserType.IE);
        return new AbstractBrowser(() -> new InternetExplorerDriver(BrowserConfigs.newIECapabilities()), BrowserType.IE);
    }

    public Browser newMicrosofatEdge() {
        if (Config.USING_SELENIUM_HUB) {
            return new AbstractBrowser(() -> createRemoteWebDriver(BrowserConfigs.newEdgeCapabilities()), BrowserType.EDGE);
        }
        throw new UnsupportedOperationException("EDGE works only with SELENIUM HUB");
    }

    /**
     * Configure and create a Chrome browser
     *
     * @return Browser
     */
    public Browser newChromeBrowser() {
        if (!Config.USING_SELENIUM_HUB) {
            configDriverPath(BrowserType.CHROME);
            return new AbstractBrowser(() -> new ChromeDriver(BrowserConfigs.newChromeCapabilities()), BrowserType.CHROME);
        }
        return new AbstractBrowser(() -> createRemoteWebDriver(BrowserConfigs.newChromeCapabilities()), BrowserType.CHROME);
    }

    /**
     * Set up and create a FireFox browser. If the path to the executable file of the browser is correct, then
     * FirefoxProfile is used.
     * If the path to the executable file of the browser is not defined or is incorrect, then Capabilities
     *
     * @return Browser
     */
    public Browser newFirefoxBrowser() {
        if (Config.USING_SELENIUM_HUB) {
            return new AbstractBrowser(() -> createRemoteWebDriver(BrowserConfigs.newFireFoxCapabilities()), BrowserType.FIREFOX);
        }
        configDriverPath(BrowserType.FIREFOX);
        return new AbstractBrowser(() -> new FirefoxDriver(BrowserConfigs.newFireFoxCapabilities()), BrowserType.FIREFOX);
    }

    public Browser newSafariBrowser() {
        configDriverPath(BrowserType.SAFARI);
        throw new UnsupportedOperationException("Safari unsupported yet");
    }

    //PRIVATE
    private void configDriverPath(BrowserType browser) {
        if (!Config.DRIVER_PATH_ENV.isEmpty() && Files.exists(Paths.get(Config.DRIVER_PATH_ENV))) {
            log.info("The driver file exists in the path : " + Config.DRIVER_PATH_ENV);
            System.setProperty("webdriver." + browser.getName() + ".driver", Config.DRIVER_PATH_ENV);
        } else {
            log.info("The driver file does not exists in the path : " + Config.DRIVER_PATH_ENV);
            URL driverDirUrl = Browser.class.getResource("/" + browser.getDriver());
            if (driverDirUrl == null) {
                throw new BrowserException("No driver file found to start the browser");
            }
            System.setProperty("webdriver." + browser.getName() + ".driver", driverDirUrl.getPath());
        }
    }

    private RemoteWebDriver createRemoteWebDriver(Capabilities capabilities) {
        URL url = null;
        try {
            url = new URL(Config.SELENIUM_HUB);
        } catch (MalformedURLException e) {
            log.error("Incorrect URL for Selenium Hub : " + Config.SELENIUM_HUB, e);
        }
        final URL fUrl = url;
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(fUrl, capabilities);
        remoteWebDriver.setFileDetector(new LocalFileDetector());
        return remoteWebDriver;
    }
}
