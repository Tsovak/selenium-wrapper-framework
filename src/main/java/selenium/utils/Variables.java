package selenium.utils;

import lombok.Getter;
import lombok.Setter;
import selenium.reports.Reporter;

/**
 * Created by Tsovak_Sahakyan.
 */
public class Variables{

    @Setter
    @Getter
    public static Reporter.Builder reporterBuilder = new Reporter.Builder();

    public static String getEnvironmentVariable(String name, String defaultValue) {
        String value = System.getenv(name.toUpperCase());

        if (value != null && !value.isEmpty()) {
            if (reporterBuilder != null) {
                reporterBuilder.append(String.format("Get Env parameter %s; value = %s", name, value));
            }
        } else {
            if (defaultValue != null) {
                if (reporterBuilder != null) {
                    reporterBuilder.append(String.format("Can not get Env parameter %s; default value = %s", name, defaultValue));
                }
                value = defaultValue;
            } else {
                if (reporterBuilder != null) {
                    reporterBuilder.append(String.format("Can not get Env parameter %s; NO DEFAULT VALUE", name));
                }
            }
        }
        return value;
    }

    public static String getEnvironmentVariable(String name) {
        return getEnvironmentVariable(name, null);
    }

}
