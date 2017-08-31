package selenium.exceptions;

/**
 * Created by Tsovak_Sahakyan.
 */
public class DriverNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 8423386770171485903L;

    public DriverNotFoundException(String message) {
        super(message);
    }

}
