package selenium.decorator;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import selenium.elements.Element;
import selenium.exceptions.ElementException;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Tsovak_Sahakyan.
 */
@SuppressWarnings("JavaReflectionMemberAccess")
@Slf4j
class ElementFactory implements ItemFactory<Element> {

    /**
     * Create Component
     *
     * @param elementClass The component class (No interface or abstract class)
     * @param wrappedElement Base element of WebElement
     * @param <E> The component class
     * @return Instance of the component class
     */
    public <E extends Element> E create(final Class<E> elementClass, final WebElement wrappedElement) {
        try {
            return elementClass.getDeclaredConstructor(WebElement.class).newInstance(wrappedElement);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ElementException(
                String.format("Failed to create the instance of the component '%s' for the base element'%s'",
                              elementClass.getSimpleName(), wrappedElement.toString()),
                e);
        }
    }
}