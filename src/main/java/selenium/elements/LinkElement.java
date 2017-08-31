package selenium.elements;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class LinkElement extends BaseElement {

    public LinkElement(By by) {
        super(by);
    }

    public LinkElement(WebElement webElement) {
        super(webElement);
    }
}