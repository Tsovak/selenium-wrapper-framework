package selenium.elements;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class CheckboxElement extends BaseElement {

    public CheckboxElement(By by) {
        super(by);
    }

    public CheckboxElement(WebElement webElement) {
        super(webElement);
    }

    public void check() {
        if (!isChecked())
            callAction(this::click, "check");
    }

    @Override
    public Element click() {
        getLabelToClick().click();
        return this;
    }

    @Override
    public boolean isReady() {
        return isEnabled();
    }

    public boolean isChecked() {
        return Boolean.valueOf(getAttribute("checked"));
    }

    public void uncheck() {
        if (isChecked())
            callAction(this::click, "uncheck");
    }


    private WebElement getLabelToClick() {
        if (!"div".equals(getWebElement().getTagName())) {
            List<WebElement> els = getWebElement().findElements(By.tagName("div"));
            if (!els.isEmpty()) {
                return els.get(0);
            } else {
                return getWebElement().findElement(By.xpath("./.."));
            }

        }
        return getWebElement();
    }
}