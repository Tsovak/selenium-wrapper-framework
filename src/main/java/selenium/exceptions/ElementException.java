package selenium.exceptions;

import org.openqa.selenium.WebDriverException;

/**
 * Created by Tsovak_Sahakyan.
 */
public class ElementException extends WebDriverException{

    private final static String MESSAGE = "Error working with web elements. ";

    public ElementException() {
        super(MESSAGE);
    }

    public ElementException(String message) {
        super(MESSAGE + message);
    }

    public ElementException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }

    public ElementException(Throwable cause) {
        super(cause);
    }
}
