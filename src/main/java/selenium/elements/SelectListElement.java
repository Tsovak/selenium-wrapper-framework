package selenium.elements;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class SelectListElement extends AbstractElement implements SelectList {

    public SelectListElement(WebElement avatar) {
        super(avatar);
    }

    public SelectListElement(By by) {
        super(by);
    }

    @Override
    protected void callPreActions() {

    }


    @Override
    public String getSelectedOption() {
        return new Select(getWebElement()).getFirstSelectedOption().getText();
    }

    @Override
    public void select(String optionText) {
        new Select(getWebElement()).selectByVisibleText(optionText);
    }

    @Override
    public void selectIfContains(String optionText) {
        List<String> options = getAvailableOptions().stream()
            .filter(opt -> opt.contains(optionText))
            .collect(Collectors.toList());
        if (options.isEmpty()) throw new NoSuchElementException("Unable to locate select list item containing: " + optionText);
        select(options.get(0));
    }

    @Override
    public List<String> getAvailableOptions() {
        return new Select(getWebElement()).getOptions()
            .stream()
            .map(option -> option.getText())
            .collect(Collectors.toList());
    }

    @Override
    public boolean isReady() {
        return false;
    }
}
