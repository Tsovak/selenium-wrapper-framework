package selenium.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Tsovak_Sahakyan.
 */
public class FileUploadElement extends AbstractElement {


    public FileUploadElement(WebElement avatar) {
        super(avatar);
    }

    public FileUploadElement(By by) {
        super(by);
    }

    public void type(final String text) {
        if (text == null) return;
        waitUntilDisplayed();
        getWebElement().sendKeys(text);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    protected void callPreActions() {

    }
}
