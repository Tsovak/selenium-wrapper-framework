package selenium.elements;

import com.jayway.awaitility.Awaitility;
import com.jayway.awaitility.core.ConditionFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriverException;
import selenium.configuration.InternalConfig;
import selenium.reports.Reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tsovak_Sahakyan.
 */
public class Waiter{
    public static final int INTERACT_WAIT_S = InternalConfig.ACTION_TIMEOUT;
    public static final int DISPLAY_WAIT_S = InternalConfig.WAIT_TIMEOUT;
    public static final int INTERVAL_MS = InternalConfig.INTERVAL_MS;

    public static ConditionFactory await() {
        return Awaitility.await().ignoreExceptionsInstanceOf(WebDriverException.class);
    }


    /**
     * Waiting with return value without Exception.
     * @param sec waiting timeout seconds
     * @param callable waiting function
     * @return Pair : Wait result, elapsed timeout
     */
    public static Pair<Boolean, Integer> wait(int sec, Callable<Boolean> callable) {
        final List<Integer> time = new ArrayList<>();
        boolean result = true;
        try {
            Awaitility.await().conditionEvaluationListener((c) -> time.add((int) c.getElapsedTimeInMS()))
                .pollInterval(INTERVAL_MS, TimeUnit.MILLISECONDS)
                .ignoreExceptions().atMost(sec, TimeUnit.SECONDS).until(callable);
        } catch (Exception skipEx){
            result = false;
        }

        int resTime = sec * 1000;
        if (!time.isEmpty()){
            resTime = time.get(time.size() - 1);
        }
        return Pair.of(result, resTime);
    }

    /**
     * Calling for a wait, checking the result and writing to the report
     * @param timeout sec
     * @param condition waiting condition
     * @param shortConditionText short text for the log
     * @param fullDescription A complete description of the waiting object for the log (for example WebElement.toString())
     */
    public static void waitCheck(int timeout, Callable<Boolean> condition, String shortConditionText, String fullDescription) {
        final Pair<Boolean, Integer> result = Waiter.wait(timeout, condition);
        final String MESSAGE =
            "Waiting for condition : " + shortConditionText + ". Timeout : " + timeout + " sec" + ". Time passed : " + result.getValue() + " ms.";
        if (result.getKey()) {

            if (InternalConfig.WAIT_REPORTING) {
                Reporter.info(MESSAGE + " - SUCCESS", fullDescription);
            }

        } else {
            Reporter.error(MESSAGE + " - FAIL", fullDescription);
        }
    }

    public static void sleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException skipEx) {
        }
    }
}
