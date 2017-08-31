package selenium.configuration;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import selenium.utils.Variables;

/**
 * Created by Tsovak_Sahakyan.
 */
@UtilityClass
@Slf4j
public class Config{

    /**
     * Path to the folder with the driver
     */
    public static final String DRIVER_PATH_ENV = Variables.getEnvironmentVariable("DRIVERPATH", "/usr/bin/chromedriver");

    /**
     * Path to the executable browser file
     */
    public static final String BROWSER_BIN_PATH_ENV = Variables.getEnvironmentVariable("BROWSERPATH", "");

    /**
     * Name of browser. Default value "chrome"
     */
    public static final String BROWSER_NAME_ENV = Variables.getEnvironmentVariable("BROWSER", "chrome");

    /**
     * Specifies whether to use the local driver or remote. Default value "false"
     */
    public static final Boolean USING_SELENIUM_HUB = Boolean.parseBoolean(Variables.getEnvironmentVariable("USING_SELENIUM_HUB", "false"));

    /**
     * Specifies whether to use the device under the Chrome browser. Default value "false"
     */
    public static final Boolean USING_CHROME_DEVICE = Boolean.parseBoolean(Variables.getEnvironmentVariable("USING_CHROME_DEVICE", "false"));

    /**
     * Emulates the device under the Chrome browser. Default value "iPhone 6 Plus"
     */
    public static final String CHROME_DEVICE = Variables.getEnvironmentVariable("CHROME_DEVICE", "iPhone 6 Plus");

    /**
     * Size of browser. Default value "fullscreen". For example: 1024,768
     */
    public static final String BROWSER_SIZE = Variables.getEnvironmentVariable("BROWSER_SIZE", "fullscreen");

    /**
     * selenium.hub.url
     */
    public static final String SELENIUM_HUB = Variables.getEnvironmentVariable("selenium.hub.url", "http://localhost:4444/wd/hub/");


}
