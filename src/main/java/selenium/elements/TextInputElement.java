package selenium.elements;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.jayway.awaitility.core.ConditionTimeoutException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Created by Tsovak_Sahakyan.
 */
public class TextInputElement extends BaseElement {

    public TextInputElement(WebElement avatar) {
        super(avatar);
    }

    public TextInputElement(By by) {
        super(by);
    }


    /**
     * Clears out the value of the input field first then types specified text.
     *
     * @param text the text to put into the text area
     */
    public TextInputElement type(final String text) {
        if (text == null)
            return this;
        return callAction(() -> {
            getWebElement().clear();
            getWebElement().sendKeys(text);
            return this;
        }, "Text input : " + text);
    }

    public void clear() {
        getWebElement().clear();
    }

    /**
     * Only sends the given keystroke
     *
     * @param key the Keys to put into the text area
     */
    public TextInputElement pressKey(final Keys key) {
        if (key == null) return this;
        sendKeys(key);
        return this;
    }

    /**
     * Only sends the given keystroke
     *
     * @param key the Keys to put into the text area
     */
    public TextInputElement pressKey(final String key) {
        if (key == null) return this;
        sendKeys(key);
        return this;
    }

    /**
     * Appends the specified text to the input field.  If there is no pre-existing text,
     * this will have the same affect as type(String).
     *
     * @param text the text to append to the current text within the input field
     */
    public TextInputElement appendType(final String text) {
        return pressKey(text);
    }

    /**
     * Prepends the specified text to the input field.  If there is no pre-existing text,
     * this will have the same affect as type(String).
     *
     * @param text the text to append to the current text within the input field
     */
    public TextInputElement prependType(final String text) {
        if (text == null) return this;
        pressKey(Keys.chord(Keys.COMMAND, Keys.ARROW_UP) + text);
        moveCursorToEndOfInput();
        return this;
    }

    private void moveCursorToEndOfInput() {
        getWebElement().sendKeys(Keys.chord(Keys.COMMAND, Keys.ARROW_DOWN));
        getWebElement().click();
    }

    public boolean waitIsEmpty() {
        try {
            Waiter.await().atMost(1, SECONDS)
                .ignoreExceptions()
                .until(() -> {
                    if (isDisplayed()) {
                        return StringUtils.isBlank(getWebElement().getText().trim());
                    } else {
                        return true;
                    }
                });
            return true;
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

}