package selenium.reports;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.UnreachableBrowserException;
import selenium.browsers.Browser;
import selenium.browsers.Browsers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.imageio.ImageIO;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class Screenshot implements ScreenShooter{

    private static final String screenShotDirectory = getDirectory("screenshots");
    private static final String htmlScreenShotDirectory = getDirectory("html-screenshots");



    @Override
    public byte[] takeBrowserScreenshot() {
        if (Browsers.hasBrowser()) {
            try {
                log.info("Creating Screenshots");
                return Browsers.getBrowser().actions().takeScreenshotByte();
            } catch (UnreachableBrowserException e) {
                log.error(" Unable to take screenshot: " + e.toString());
            }
        } else {
            log.warn("Could not make screenshot of Browser. Most likely it is not started");

        }
        return new byte[0];
    }

    @Override
    public byte[] takeDesktopScreenshot() {
        BufferedImage originalImage = getScreenAsBufferedImage();
        if (originalImage == null) {
            return new byte[0];
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            ImageIO.write(originalImage, "png", outputStream);
            outputStream.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.warn("Could not make a screenshot of the Desktop", e);
        }
        return new byte[0];
    }

    public static void takeScreenshot(String filename) {
        if (log.isDebugEnabled()) {
            takeFullDesktopScreenshot(filename);
        } else {
            if (Browsers.hasBrowser()) {
                try {
                    filename +=  "_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId() + ".png";
                    File scrFile = Browsers.getBrowser().actions().takeScreenshot();
                    log.info("Creating Screenshot: " + screenShotDirectory + filename);
                    FileUtils.copyFile(scrFile, new File(screenShotDirectory + filename));
                } catch (IOException | UnreachableBrowserException e) {
                    log.error(" Unable to take screenshot: " + e.toString());
                }
            } else {
                log.error("Webdriver not started. Unable to take screenshot");
            }
        }
    }

    public static void takeFullDesktopScreenshot(String filename) {
        try {
            filename +=  "_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId() + ".png";
            BufferedImage img = new Screenshot().getScreenAsBufferedImage();
            File output = new File(filename);
            ImageIO.write(img, "png", output);
            log.info("Creating FULL SCREEN Screenshot: " + screenShotDirectory + filename);
            FileUtils.copyFile(output, new File(screenShotDirectory + filename));
            FileUtils.deleteQuietly(output);
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public static void takeHTMLScreenshot(String filename) {
        if (!Browsers.hasBrowser()) {
            log.error("Webdriver not started. Unable to take html snapshot");
            return;
        }

        filename +=  "_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId() + ".html";

        Writer writer = null;
        log.info("Capturing HTML snapshot: " + htmlScreenShotDirectory + filename);

        try {
            writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(htmlScreenShotDirectory + filename), "utf-8"));
            writer.write(Browsers.getBrowser().actions().getHtml());
        } catch (IOException ex) {
            log.info("Unable to write out current state of html");
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                log.info("Unable to close writer");
            }
        }
    }

    private BufferedImage getScreenAsBufferedImage() {
        BufferedImage img = null;
        try {
            Robot r;
            r = new Robot();
            Toolkit t = Toolkit.getDefaultToolkit();
            Rectangle rect = new Rectangle(t.getScreenSize());
            img = r.createScreenCapture(rect);
        } catch (AWTException e) {
            log.error(e.toString());
        }
        return img;
    }

    private static String getDirectory(String name) {
        String screenshotDirectory = String.format("%s../%s/", ClassLoader.getSystemClassLoader().getSystemResource("").getPath(),name);
        File file = new File(screenshotDirectory);
        if (!file.exists()) file.mkdir();
        log.info("Creating screenshot directory: " + screenshotDirectory);
        return screenshotDirectory;
    }
}
