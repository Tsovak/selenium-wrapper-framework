package selenium.reports;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class Attachment{

    private ScreenShooter screenShooter;

    public Attachment() {
        screenShooter = new Screenshot();
    }

    public Attachment(ScreenShooter screenShooter) {

        this.screenShooter = screenShooter;
    }

    /**
     * Attach a screenshot to the Allure report
     */
    @ru.yandex.qatools.allure.annotations.Attachment(value = "Screenshot : {0}",
        type = "image/png")
    protected byte[] attachScreen(String screenName) {
        if (screenShooter != null) {
            byte[] result = screenShooter.takeBrowserScreenshot();
            if (result == null || result.length == 0) {
                result = screenShooter.takeDesktopScreenshot();
            }
            return result;
        } else {
            return new byte[0];
        }
    }

    /**
     * Attach a file to the Allure report
     */
    @ru.yandex.qatools.allure.annotations.Attachment(value = "File : {0}")
    protected byte[] attachFile(String fileDesc, Path path) {

        byte[] result = new byte[0];
        try {
            result = Files.readAllBytes(path);
        } catch (IOException e) {
            log.warn("Error reading file for attachment: " + fileDesc, e);
        }
        return result;
    }

    /**
     * Attach a file to the Allure report
     */
    @ru.yandex.qatools.allure.annotations.Attachment(value = "File : {0}")
    protected byte[] attachFile(String fileName, byte[] file) {

        return file;
    }

    /**
     * Attach text (when there a lot of) in the Allure report
     */
    @ru.yandex.qatools.allure.annotations.Attachment(value = "Text : {0}",
        type = "text/plain")
    protected String attachText(String title, String text) {
        return text;
    }

    /**
     * Attach text to the Allure report
     */
    @ru.yandex.qatools.allure.annotations.Attachment(value = "Html : {0}",
        type = "text/html")
    protected String attachHtml(String title, String html) {
        return html;
    }
}
