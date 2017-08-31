package selenium.elements;

import org.openqa.selenium.By;
import selenium.browsers.Browsers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tsovak_Sahakyan.
 */
public class ElementFactory{


    public static BaseElement createBaseElement(By by) {
        return new BaseElement(by);
    }


    public static List<BaseElement> createBaseElements(By by) {
        return Browsers.getBrowser().getDriver().findElements(by).stream().map(BaseElement::new).collect(Collectors.toList());

    }


    public static ImageElement createImageElement(By by) {
        return new ImageElement(by);

    }


    public static List<ImageElement> createImageElements(By by) {
        return Browsers.getBrowser().getDriver().findElements(by).stream().map(ImageElement::new).collect(Collectors.toList());
    }


    public static LinkElement createLinkElement(By by) {
        return new LinkElement(by);
    }


    public static List<LinkElement> createLinkElements(By by) {
        return Browsers.getBrowser().getDriver().findElements(by).stream().map(LinkElement::new).collect(Collectors.toList());
    }


    public static TextInputElement createTextInputElement(By by) {
        return new TextInputElement(by);
    }


    public static List<TextInputElement> createTextInputElements(By by) {
        return Browsers.getBrowser().getDriver().findElements(by).stream().map(TextInputElement::new).collect(Collectors.toList());

    }


    public static SelectListElement createSelectListElement(By by) {
        return new SelectListElement(by);
    }


    public static List<SelectListElement> createSelectListElements(By by) {
        return Browsers.getBrowser().getDriver().findElements(by).stream().map(SelectListElement::new).collect(Collectors.toList());

    }


    public static FileUploadElement createFileUploadElement(By by) {
        return new FileUploadElement(by);
    }


    public static TableElement createTableElement(By by) {
        return new TableElement(by);
    }


    public static List<TableElement> createTableElements(By by) {
        return Browsers.getBrowser().getDriver().findElements(by).stream().map(TableElement::new).collect(Collectors.toList());

    }


    public static TableRowElement createTableRowElement(By by) {
        return new TableRowElement(by);
    }


    public static List<TableRowElement> createTableRowElements(By by) {
        return Browsers.getBrowser().getDriver().findElements(by).stream().map(TableRowElement::new).collect(Collectors.toList());

    }


    public static ButtonElement createButtonElement(By by) {
        return new ButtonElement(by);
    }


    public static List<ButtonElement> createButtonElements(By by) {
        return Browsers.getBrowser().getDriver().findElements(by).stream().map(ButtonElement::new).collect(Collectors.toList());
    }


    public static CheckboxElement createCheckboxElement(By by) {
        return new CheckboxElement(by);
    }


    public static List<CheckboxElement> createCheckboxElements(By by) {
        return Browsers.getBrowser().getDriver().findElements(by).stream().map(CheckboxElement::new).collect(Collectors.toList());

    }
}
