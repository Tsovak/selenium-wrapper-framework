package selenium.site.com.google;

import lombok.Getter;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import selenium.annotations.Title;
import selenium.elements.ButtonElement;
import selenium.elements.TextInputElement;
import selenium.site.BasePage;

import java.util.List;

/**
 * Created by Tsovak_Sahakyan.
 */
public class MainPage extends BasePage<MainPage>{

    @Getter
    @FindBy(name = "q")
    @Title(value = "This is search input element")
    TextInputElement searchInputElement;

    @Getter
    @FindAll({
        @FindBy(xpath = "(//*[@name='btnK'])[2]")})
    @Title(value = "Search Google")
    ButtonElement searchButton;

    @Getter
    @FindBy(css = "input[name='btnI']")
    @Title(value = "I'm Feeling Lucky")
    ButtonElement luckyButton;

    @Getter
    @FindBy(css = "#rso > div > div > div")
    @Title(value = "The search results")
    List<SearchItem> searchItems;

    public MainPage() {
        super();
    }

    @Override
    public boolean isOpen() {
        return searchInputElement.isDisplayed();
    }

    @Override
    public MainPage waitUntilPageToBeOpen() {
        waitJSReady();
        searchInputElement.waitUntilDisplayed();
        return this;
    }
}
