package selenium.elements;

import static java.util.concurrent.TimeUnit.SECONDS;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class ImageElement extends BaseElement {

    public ImageElement(By by) {
        super(by);
    }

    public ImageElement(WebElement webElement) {
        super(webElement);
    }

    public String getImageSource() {
        Waiter.await().atMost(Waiter.INTERACT_WAIT_S, SECONDS).until(() -> StringUtils.isNotBlank(getAttribute("src")) || StringUtils.isNotBlank(getCssValue("background-image")));
        if (StringUtils.isNotBlank(getAttribute("src"))) {
            return getAttribute("src");
        } else {
            return getCssValue("background-image");
        }
    }
}