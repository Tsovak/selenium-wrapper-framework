package selenium.browsers;

import java.util.concurrent.TimeUnit;

/**
 * Created by Tsovak_Sahakyan.
 */
public interface PageLoadWaiter {

    boolean isSatisfied();

    TimeUnit getTimeUnit();

    int getTimeout();
}

