package selenium.decorator;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import selenium.exceptions.ElementException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Tsovak_Sahakyan.
 */
@Slf4j
public class ElementHandler implements InvocationHandler{

    /**
     * Caching
     */
    private static final boolean CACHED = true;

    private final ElementLocator locator;

    private WebElement cachedElement = null;

    ElementHandler(ElementLocator locator) {
        this.locator = locator;
    }

    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        final String methodName = method.getName();
        log.debug("[PROXY] The method '{}' is called with the arguments '{}' for the object '{}'", methodName, Arrays
            .toString(objects), locator.toString());
        try {
            if ("toString".equals(method.getName())) {
                return "Proxy element for: " + locator.toString();
            }
            if ("getWrappedElement".equals(method.getName())) {
                return getElement();
            }

            return method.invoke(getElement(), objects);
        } catch (InvocationTargetException e) {
            cachedElement = null;
            log.warn("[PROXY] Caught exception InvocationTargetException '{}'. The locator '{}'. The method '{}'", e.getCause().getClass().getSimpleName(),
                     locator.toString(), methodName);

            throw new ElementException(
                String.format("[PROXY] Can not call the method '%s' for the base element with the locator '%s'", method.getName(), locator.toString()),
                e.getCause());
        }
    }

    private WebElement getElement() {
        if (CACHED && Objects.nonNull(cachedElement)) {
            log.debug("[PROXY] Items from the cache were received. Locator '{}'", locator.toString());
            return cachedElement;
        }
        cachedElement = locator.findElement();
        log.debug("[PROXY] The element was searched. Locator '{}'", locator.toString());
        return cachedElement;
    }
}

