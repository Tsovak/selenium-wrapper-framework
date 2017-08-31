package selenium.decorator;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import selenium.browsers.Browsers;
import selenium.elements.Element;
import selenium.elements.Waiter;

/**
 * Base class for initializing PageObject elements. Performs only initialization of elements with the FindBy annotation
 *Created by Tsovak_Sahakyan.
 */
@SuppressWarnings("WeakerAccess")
@Slf4j
abstract public class BaseContainer implements Container{

    public static final int DEFAULT_WAIT_TIMEOUT = 15; // WebDriverWait default timeout in seconds

    /**
     * Without a base element. When initializing all container elements with annotations using the root element (i.e.,
     * full paths)
     */
    protected BaseContainer() {
        log.debug("Creating a Class Container {}", getClass().getSimpleName());
        initElements();
    }

    protected BaseContainer(WebElement baseElement) {
        log.debug("Creating a Class Container {} based on the base element {}", getClass().getSimpleName(),
                  baseElement.toString());
        initElements(baseElement);
    }

    protected BaseContainer(Element baseElement) {
        log.debug("Creating a Class Container {} based on the base element {}", getClass().getSimpleName(),
                  baseElement.toString());
        initElements(baseElement.getWebElement());
    }

    @Override
    public void waitBeforeInit() {
        log.debug("Waiting for JS to run before initializing the container components");
        if (!waitJSReady()) {
            log.error(
                "Exceeded timeout waiting for JS page loading before initializing the class components '{}'. "
                + "However, the execution will continue, because The probability of successful initialization is high",
                getClass().getSimpleName());
        }
    }

    /**
     * Waiting for JS to run on the page
     *
     * @return Results of waiting
     */
    protected boolean waitJSReady() {
        return Waiter.wait(DEFAULT_WAIT_TIMEOUT,
                           () -> Browsers.getBrowser().actions()
                               .executeScript("return document.readyState")
                               .equals("complete"))
            .getKey();
    }

}