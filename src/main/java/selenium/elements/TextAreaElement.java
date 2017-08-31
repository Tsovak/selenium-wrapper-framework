package selenium.elements;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class TextAreaElement extends BaseElement {

    public TextAreaElement(By by) {
        super(by);
    }

    public TextAreaElement(WebElement webElement) {
        super(webElement);
    }
}