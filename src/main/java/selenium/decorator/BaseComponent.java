package selenium.decorator;

import org.openqa.selenium.WebElement;
import selenium.elements.Element;

/**
 * Created by Tsovak_Sahakyan.
 */
public abstract class BaseComponent extends BaseContainer implements Component {

    private final WebElement baseElement;
    /**
     * Transferred element when initializing all container elements with annotations
     * @param baseElement
     */
    protected BaseComponent(WebElement baseElement) {
        super(baseElement);
        this.baseElement = baseElement;
    }

    protected BaseComponent(Element baseElement) {
        super(baseElement);
        this.baseElement = baseElement.getWebElement();
    }


    @Override
    public WebElement getBaseElement(){
        return baseElement;

    }

    @Override
    public boolean isReady() {
        return waitIsReady();
    }

    @Override
    public String getText() {
        return getBaseElement().getText();
    }
}
