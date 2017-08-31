package selenium.decorator;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.openqa.selenium.WebElement;
import selenium.elements.Waiter;

/**
 * Created by Tsovak_Sahakyan.
 */
public interface Component extends Container {
    WebElement getBaseElement();

    String getText();

    boolean isReady();

    default boolean waitIsReady(){
        Waiter.await().ignoreExceptions().atMost(Waiter.DISPLAY_WAIT_S, SECONDS).until(() -> !isReady());
        return !isReady();
    }

//    default void initElements(){
//        waitIsReady();
//    }

}

