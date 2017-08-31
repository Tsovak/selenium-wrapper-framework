package selenium.decorator;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import selenium.exceptions.ElementException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class ComponentFactory implements ItemFactory<Component> {

    /**
     * Create Component
     *
     * @param componentClass The component class (No interface or abstract class)
     * @param wrappedElement Base element of WebElement
     * @param <E> The component class
     * @return Instance of the component class
     */
    public <E extends Component> E create(final Class<E> componentClass, final WebElement wrappedElement) {
        try {
            Constructor<E> declaredConstructor = componentClass.getDeclaredConstructor(WebElement.class);
            if(!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return declaredConstructor.newInstance(wrappedElement);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ElementException(String.format(
                "Failed to create the instance of the component '%s' for the base element'%s'",
                componentClass.getSimpleName(), wrappedElement.toString()), e);
        }
    }
}