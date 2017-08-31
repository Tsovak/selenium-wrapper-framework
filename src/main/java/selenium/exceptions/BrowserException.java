package selenium.exceptions;

/**
 * Created by Tsovak_Sahakyan.
 */
public class BrowserException extends RuntimeException{
    private final static String MESSAGE = "Browser error. ";
    public BrowserException() {
        super(MESSAGE);
    }

    public BrowserException(String message) {
        super(MESSAGE + message);
    }

    public BrowserException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }

    public BrowserException(Throwable cause) {
        super(cause);
    }
}
