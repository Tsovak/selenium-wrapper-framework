package selenium.site;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import selenium.browsers.Browsers;
import selenium.decorator.BaseContainer;
import selenium.site.com.google.Constants;

/**
 * Created by Tsovak_Sahakyan.
 */
public abstract class BasePage<T> extends BaseContainer{

    public BasePage() {
        super();
    }

    public BasePage(String url) {
        open(url);
        super.initElements();
    }

    public void open(String urlPath) {
        Browsers.getBrowser().get(Constants.URL + urlPath);
        super.initElements();
    }

    public T open(){
        Browsers.getBrowser().get(Constants.URL);
        super.initElements();
        return (T) this;
    }


    public boolean isOpen(String url) {
        return Browsers.getBrowser().getDriver().getCurrentUrl().contains(url);
    }

    public abstract boolean isOpen();


    public abstract T waitUntilPageToBeOpen();

    public void waitASecond(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
