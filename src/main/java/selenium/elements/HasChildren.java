package selenium.elements;

import org.openqa.selenium.By;

import java.util.List;

/**
 * Created by Tsovak_Sahakyan.
 */
public interface HasChildren{

    BaseElement createBaseElement(By by);

    List<BaseElement> createBaseElements(By by);

    ImageElement createImageElement(By by);

    List<ImageElement> createImageElements(By by);

    LinkElement createLinkElement(By by);

    List<LinkElement> createLinkElements(By by);

    TextInputElement createTextInputElement(By by);

    List<TextInputElement> createTextInputElements(By by);

    SelectListElement createSelectListElement(By by);

    List<SelectListElement> createSelectListElements(By by);

    FileUploadElement createFileUploadElement(By by);

    TableElement createTableElement(By by);

    List<TableElement> createTableElements(By by);

    TableRowElement createTableRowElement(By by);

    List<TableRowElement> createTableRowElements(By by);

    ButtonElement createButtonElement(By by);

    List<ButtonElement> createButtonElements(By by);

    CheckboxElement createCheckboxElement(By by);

    List<CheckboxElement> createCheckboxElements(By by);

}
