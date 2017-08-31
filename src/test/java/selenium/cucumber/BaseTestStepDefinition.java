package selenium.cucumber;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import selenium.browsers.Browser;
import selenium.browsers.BrowserType;
import selenium.browsers.Browsers;
import selenium.reports.Reporter;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class BaseTestStepDefinition {

    @Before
    public void beforeScenario(Scenario scenario) {
        Browsers.newBrowser(BrowserType.CHROME).start();
    }

    @After(order = 10)
    public void afterScenario(Scenario scenario) {
        Reporter.info("The end of scenario : " + scenario.getName());
        try {
            if (scenario.isFailed()) {
                Reporter.error("Has error on scenario : " + scenario.getName());
                log.error("Has error on scenario " + scenario.getName());
            }
            Reporter.screen();
        }
        finally {
            Browsers.stopAllBrowsers();
        }
    }
}
