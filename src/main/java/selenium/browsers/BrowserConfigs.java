package selenium.browsers;

import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import selenium.configuration.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by Tsovak_Sahakyan.
 */
@UtilityClass
public final class BrowserConfigs {

    public static DesiredCapabilities newCommonCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
        capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);

        return mergeLoggingPreferences(capabilities);

    }

    private DesiredCapabilities mergeLoggingPreferences(DesiredCapabilities capabilities) {
        LoggingPreferences logging = new LoggingPreferences();
        logging.enable(LogType.PERFORMANCE, Level.ALL);
        logging.enable(LogType.BROWSER, Level.ALL);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logging);
        return capabilities;
    }

    public static Capabilities newChromeCapabilities() {

        Map<String, Object> prefs = ImmutableMap.<String, Object>builder().
            put("profile.default_content_settings.popups", 0).
            put("download.default_directory", ".").
            put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1).
            put("download.prompt_for_download", "false").
            build();
        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities capabilities = newCommonCapabilities();

        if (Config.USING_CHROME_DEVICE) {
            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", "Apple iPhone 5");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }

        options.setExperimentalOption("prefs", prefs);
        if (!Config.BROWSER_SIZE.equalsIgnoreCase("FULLSCREEN") && !Config.USING_CHROME_DEVICE) {
            options.addArguments("window-size=" + Config.BROWSER_SIZE, "--no-sandbox");
        } else {
            options.addArguments("--start-maximized");
        }
        if (!Config.BROWSER_BIN_PATH_ENV.isEmpty() && !Config.USING_SELENIUM_HUB) {
            options.setBinary(Config.BROWSER_BIN_PATH_ENV);
        }
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setBrowserName("chrome");
        return capabilities;
    }

    public static Capabilities newFireFoxCapabilities() {
        //DesiredCapabilities capabilities = newCommonCapabilities();
        //capabilities.setCapability("marionette", true);

        DesiredCapabilities capabilities = new FirefoxOptions().setProfile(newFirefoxProfile()).addTo(DesiredCapabilities.firefox());
        capabilities.merge(newCommonCapabilities());
        return capabilities;
    }

    public static FirefoxProfile newFirefoxProfile() {
        FirefoxProfile profile = new FirefoxProfile();

        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setPreference("webdriver_assume_untrusted_issuer", true);
        profile.setPreference("webdriver_accept_untrusted_certs", true);
        profile.setPreference("trustAllSSLCertificates", true);

        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", ".");
        profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.manager.focusWhenStarting", false);
        profile.setPreference("browser.download.useDownloadDir", true);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        profile.setPreference("browser.download.manager.closeWhenDone", true);
        profile.setPreference("browser.download.manager.showAlertOnComplete", false);
        profile.setPreference("browser.download.manager.useWindow", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/jpeg, image/png, image/jpg, application/pdf, image/tiff");

        return profile;
    }

    public static DesiredCapabilities newEdgeCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.edge();
        capabilities.setPlatform(Platform.WIN10);
        capabilities.setBrowserName(BrowserType.EDGE);

//        EdgeOptions edgeOptions = new EdgeOptions();
//        capabilities.setCapability(edgeOptions.CAPABILITY, edgeOptions);

        return capabilities;
    }

    public static DesiredCapabilities newIECapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.merge(newCommonCapabilities());
        capabilities.setCapability("ie.enableFullPageScreenshot", true);
        capabilities.setCapability("ignoreProtectedModeSettings", true);

        capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setJavascriptEnabled(true);

        return capabilities;
    }
}