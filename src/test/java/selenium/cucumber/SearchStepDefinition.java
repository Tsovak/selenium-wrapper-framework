package selenium.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import selenium.site.com.google.MainPage;

/**
 * Created by Tsovak_Sahakyan.
 */
public class SearchStepDefinition{

    static MainPage page = null;

    @Given("^user has opened Main Page successfully$")
    public void userHasOpenedMainPageSuccessfully() {
        page = new MainPage().open().waitUntilPageToBeOpen();
    }

    @When("^user searches '(.*)'$")
    public void userSearchesData(String searchData) throws InterruptedException {
        page.getSearchInputElement().type(searchData);
        page.getSearchInputElement().pressKey(Keys.TAB);
        page.getSearchButton().click();
        Thread.sleep(3000L);
    }


    @Then("^user see results$")
    public void userSeeResults() {
        Assert.assertTrue("No results found",page.getSearchItems().size() > 0 );
    }

}
