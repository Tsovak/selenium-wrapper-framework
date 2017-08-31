package selenium.decorator;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import selenium.exceptions.ElementException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tsovak_Sahakyan.
 */
@SuppressWarnings({"WeakerAccess", "Duplicates"})
@Slf4j
public class ListHandler<T> implements InvocationHandler{

    private final ItemFactory<T> factory;

    private final ClassLoader loader;

    private final Class<T> clazz;

    private final boolean isStatic;

    private final ElementLocator locator;

    private List<WebElement> cachedElements = new ArrayList<>();

    private List<T> cachedObjects = new ArrayList<>();

    public ListHandler(ElementLocator locator, ClassLoader loader, Class<T> clazz, ItemFactory<T> factory, boolean isStatic) {
        this.locator = locator;
        this.loader = loader;
        this.clazz = clazz;
        this.factory = factory;
        this.isStatic = isStatic;
    }


    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        final String methodName = method.getName();
        log.debug("[PROXY] The method '{}' is called with the arguments '{}' for the object '{}'", methodName, Arrays
            .toString(objects), locator.toString());

        final List<WebElement> wElements = getElements();

        /* If the new list of items has not changed in size, then immediately return the cached objects */
        if (cachedObjects.size() != wElements.size()) {

        /* else create a new list of objects */
            cachedObjects.clear();
            for (int i = 0; i < wElements.size(); i++) {
                cachedObjects.add(factory.create(clazz, proxyWebElementForList(loader, locator, i)));
            }
        }

        try {
            return method.invoke(cachedObjects, objects);
        } catch (InvocationTargetException e) {
            cachedObjects.clear();
            cachedElements.clear();
            log.warn("[PROXY] An exception is thrown InvocationTargetException '{}'. Locator '{}'. Method '{}'", e.getCause().getClass().getSimpleName(),
                     locator.toString(), methodName);

            throw new ElementException(
                String.format("[PROXY] Can not call the method '%s' for the base element with the locator '%s'", method.getName(), locator.toString()),
                e.getCause());
        }
    }

    /**
     * Proxy of one element from the list of elements. Complex logic. Each time the method is invoked,
     * all elements are searched, followed by an element with the specified index and proxy. Its proxy is returned.
     */
    private WebElement proxyWebElementForList(ClassLoader loader, ElementLocator locator, int index) {

        InvocationHandler handler = (proxy, method, args) -> {
            final String methodName = method.getName();
            log.debug("[PROXY] The method '{}' is called with the arguments '{}' for the object '{}'", methodName, Arrays.toString(args), locator.toString());

            try {
                if ("getWrappedElement".equals(method.getName())) {
                    return getElements().get(index);
                }
                return method.invoke(getElements().get(index), args);
            } catch (InvocationTargetException e) {
                cachedElements.clear();
                log.warn("[PROXY] An exception is thrown InvocationTargetException '{}'. Locator '{}'. Method '{}'", e.getCause().getClass().getSimpleName(),
                         locator.toString(), methodName);

                throw new ElementException(
                    String.format("[PROXY] Can not call the method '%s' for the base element with the locator '%s'", method.getName(), locator.toString()),
                    e.getCause());
            }
        };

        return (WebElement) Proxy
            .newProxyInstance(loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);
    }

    private List<WebElement> getElements() {
        if (isStatic) {
            if (!cachedElements.isEmpty()) {
                log.debug("[PROXY] Get items from the cache. Count of elements '{}'. Locator '{}'", cachedElements.size(), locator.toString());
                return cachedElements;
            }
        }
        cachedElements = locator.findElements();
        log.debug("[PROXY] Search and get items. Count of elements '{}'. Locator '{}'", cachedElements.size(), locator.toString());
        return cachedElements;
    }
}
