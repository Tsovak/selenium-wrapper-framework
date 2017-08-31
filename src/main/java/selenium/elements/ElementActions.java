package selenium.elements;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Created by Tsovak_Sahakyan.
 */
public interface ElementActions{

    ElementActions click();

    ElementActions doubleClick();

    String getTagName();

    String getAttribute(String name);

    String getCssValue(String name);

    String getText();

    boolean isDisplayed();

    boolean isEnabled();

    boolean isSelected();

    ElementActions focus();

    ElementActions scroll();

    ElementActions blur();

    boolean isFocused();

    ElementActions sendKeys(final Keys... keys);

    ElementActions sendKeys(final String... keys);

    boolean isReady();

    WebElement getWebElement();

    ElementActions getParentElement();
}
