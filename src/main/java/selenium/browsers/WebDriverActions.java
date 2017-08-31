package selenium.browsers;

import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.File;
import java.util.Set;

/**
 * Created by Tsovak_Sahakyan.
 */
public class WebDriverActions{

    private WebDriver driver;

    public WebDriverActions(
        @NonNull
            WebDriver driver) {
        this.driver = driver;
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public String getText() {
        return driver.findElement(By.xpath("//html")).getText();
    }

    public String getText(WebElement element) {
        String result;
        result = element.getText();
        if (result != null && !result.isEmpty()) {
            return result;
        }
        result = element.getAttribute("value");
        if (result != null && !result.isEmpty()) {
            return result;
        }
        result = element.getAttribute("innerText");
        if (result != null && !result.isEmpty()) {
            return result;
        }
        return result;
    }

    public String getHtml() {
        return driver.getPageSource();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getUserAgent() {
        return (String) executeScript("return navigator.userAgent;");
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void deleteCookie(Cookie cookie) {
        driver.manage().deleteCookie(cookie);
    }

    public void addCoookie(Cookie cookie) {
        driver.manage().addCookie(cookie);
    }

    public Cookie getCookie(String name) {
        return driver.manage().getCookieNamed(name);
    }

    public void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
    }

    public Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }

    public WebDriver.Navigation navigate() {
        return driver.navigate();
    }

    public Object executeScript(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
    }

    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    public byte[] takeScreenshotByte() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public File takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    public WebDriver.Window getWindow() {
        return driver.manage().window();
    }

    public void maximize() {
        // chrome doesn't actually always maximize so implement workaround
        if (driver instanceof ChromeDriver) {
            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            org.openqa.selenium.Point position = new org.openqa.selenium.Point(0, 0);
            driver.manage().window().maximize();
            driver.manage().window().setPosition(position);
            org.openqa.selenium.Dimension maximizedScreenSize =
                new org.openqa.selenium.Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
            driver.manage().window().setSize(maximizedScreenSize);
        } else {
            driver.manage().window().maximize();
        }
    }

    public void setSize(org.openqa.selenium.Dimension dimension) {
        driver.manage().window().setSize(dimension);
    }
}
