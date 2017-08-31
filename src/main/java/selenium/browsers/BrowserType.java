package selenium.browsers;

import com.google.common.base.Enums;
import selenium.utils.OSUtil;


/**
 * Created by Tsovak_Sahakyan.
 */
public enum BrowserType{
    CHROME("chromedriver"),
    FIREFOX("geckodriver"),
    IE("IEDriverServer"),
    SAFARI(""),
    EDGE(""),
    OTHER("");

    private final String driver;


    BrowserType(String driver) {
        this.driver = driver;
    }

    public static BrowserType get(String value) {
        return Enums.getIfPresent(BrowserType.class, value.toUpperCase().replaceAll(" ", "")).or(OTHER);
    }

    public String getName() {
        if (this == FIREFOX) {
            return "gecko";
        }
        return this.toString().toLowerCase();
    }

    /**
     * Get the name of the executable driver with regard to the OS
     */
    public String getDriver() {
        if (OSUtil.getOS() == OSUtil.OS.LINUX) {
            return driver;
        }
        if (OSUtil.getOS() == OSUtil.OS.WIN) {
            return driver + ".exe";
        }
        return driver;
    }
}
