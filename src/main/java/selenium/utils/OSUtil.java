package selenium.utils;

/**
 * Created by Tsovak_Sahakyan.
 */
public class OSUtil{

    public enum OS {
        LINUX, MACOS, WIN, SOLARIS, OTHER;
    }

    /**
     * Get Operation System {@link OS}
     *
     * @return
     */
    public static OS getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return OS.WIN;
        } else if (os.contains("nux") || os.contains("nix")) {
            return OS.LINUX;
        } else if (os.contains("mac")) {
            return OS.MACOS;
        } else if (os.contains("sunos")) {
            return OS.SOLARIS;
        } else {
            return OS.OTHER;
        }
    }
}
