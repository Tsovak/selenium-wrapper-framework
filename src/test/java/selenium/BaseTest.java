package selenium;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import selenium.browsers.BrowserType;
import selenium.browsers.Browsers;

/**
 * Created by Tsovak_Sahakyan.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {
    "classpath:cucumber/features/googlesearch.feature"
})
public class BaseTest {

}
