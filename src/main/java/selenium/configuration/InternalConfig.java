package selenium.configuration;

import selenium.utils.Resource;

/**
 * Internal configuration. From the properties files
 * Created by Tsovak_Sahakyan.
 */
public class InternalConfig{

    private static final Resource resources = Resource.of("framework");

    /**
     * Waiting timeout<br/>
     * elements.timeout
     */
    public static final int WAIT_TIMEOUT = resources.getInt("elements.wait.timeout", 20);

    /**
     * Timeout for actions on element<br/>
     * elements.timeout
     */
    public static final int ACTION_TIMEOUT = resources.getInt("elements.action.timeout", 5);

    /**
     * Number of attempts<br/>
     * elements.actions.try
     */
    public static final int ACTIONS_TRY = resources.getInt("elements.actions.try", 2);

    /**
     * Internal constant. On/off reporting elements<br/>
     * elements.reporting
     */
    public static final boolean ELEMENT_REPORTING = resources.getBoolean("elements.reporting", false);

    /**
     * Internal constant. On/off reporting screenshots<br/>
     * reporting.screenshot
     */
    public static final boolean SCREENINIG = resources.getBoolean("reporting.screenshot", false);

    /**
     * Internal constant. On/off logging of successful expectations<br/>
     * reporting.waits
     */
    public static final boolean WAIT_REPORTING = resources.getBoolean("reporting.waits", false);


    /**
     * Interval of checking conditions while waiting. More interval (500 ms) - more reliability. 0 is the maximum speed.
     * elements.wait.interval
     */
    public static final int INTERVAL_MS = resources.getInt("elements.wait.interval", 0);

    /**
     * reporting.video
     */
    public static final boolean LOG_VIDEO = resources.getBoolean("reporting.video", false);

}
