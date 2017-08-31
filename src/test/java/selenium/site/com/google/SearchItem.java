package selenium.site.com.google;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.annotations.Title;
import selenium.decorator.BaseComponent;
import selenium.elements.Element;
import selenium.elements.LinkElement;
import selenium.elements.TextAreaElement;

/**
 * Created by Tsovak_Sahakyan.
 */
public class SearchItem extends BaseComponent{

    @Getter
    @FindBy(css = "h3 > a")
    @Title(value = "Title of search result")
    LinkElement titleLink;

    @Getter
    @FindBy(xpath = ".//span[@class='st']")
    @Title(value = "Description")
    TextAreaElement description;



    protected SearchItem(WebElement baseElement) {
        super(baseElement);
    }

    protected SearchItem(Element baseElement) {
        super(baseElement);
    }
}
