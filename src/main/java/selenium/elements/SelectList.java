package selenium.elements;

import java.util.List;

/**
 * Created by Tsovak_Sahakyan.
 */
public interface SelectList{

    void select(String optionText);

    void selectIfContains(String optionText);

    boolean isDisplayed();

    String getSelectedOption();

    List<String> getAvailableOptions();
}
