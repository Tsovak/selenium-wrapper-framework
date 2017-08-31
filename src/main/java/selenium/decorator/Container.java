package selenium.decorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import selenium.browsers.Browsers;

/**
 * Container. Save the base element.
 * Initializes elements based on the base, if any, or from the root element.
 *
 * Created by Tsovak_Sahakyan.
 */
public interface Container{

    /**
     * Initializing the Entire Page
     */
    default void initElements() {
        waitBeforeInit();
        PageFactory.initElements(new ExtendedFieldDecorator(Browsers.getBrowser().getDriver()), this);
    }

    /**
     * Initialization of the base element
     *
     * @param baseElement
     */
    default void initElements(WebElement baseElement) {
        waitBeforeInit();
        PageFactory.initElements(new ExtendedFieldDecorator(baseElement), this);
    }

    /**
     * Waiting before initialization
     */
    void waitBeforeInit();

//    default WebDriverWait wait(int seconds) {
//        return new WebDriverWait(DriverManager.INSTANCE.getDriver(), seconds);
//    }

}
