package selenium.utils;

import java.util.ResourceBundle;

/**
 * Obtaining resources from the classpath from properties using ResourceBundle
 * Created by Tsovak_Sahakyan.
 */
public final class Resource{
    private final ResourceBundle resources;

    public Resource (String name){
        this.resources = ResourceBundle.getBundle(name);
    }

    public static Resource of(String name){
        return new Resource(name);
    }

    public String get(String key) {
        String result = "";
        try {
            result = resources.getString(key);
        } catch (Exception skipEx) {
        }
        return result;

    }

    public String getString(String key, String defaultValue) {
        final String result = get(key);
        if (result.isEmpty()) {
            return defaultValue;
        } else {
            return String.valueOf(result);
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        final String result = get(key);
        if (result.isEmpty()) {
            return defaultValue;
        } else {
            return Boolean.valueOf(result);
        }
    }

    public int getInt(String key, int defaultValue) {
        final String result = get(key);
        if (result.isEmpty()) {
            return defaultValue;
        } else {
            return Integer.parseInt(result);
        }
    }
}
