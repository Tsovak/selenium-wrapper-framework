package selenium.decorator;

import org.openqa.selenium.WebElement;

/**
 * Created by Tsovak_Sahakyan.
 */
interface ItemFactory<T> {

    <E extends T> E create(final Class<E> elementClass, final WebElement wrappedElement);
}

