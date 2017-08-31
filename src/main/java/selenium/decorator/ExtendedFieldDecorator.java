package selenium.decorator;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import selenium.elements.Element;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Tsovak_Sahakyan.
 */
@SuppressWarnings("unchecked")
@Slf4j
public class ExtendedFieldDecorator extends DefaultFieldDecorator{

    private final ElementFactory elementFactory = new ElementFactory();

    private final ComponentFactory componentFactory = new ComponentFactory();

    public ExtendedFieldDecorator(final SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
    }

    @Override
    public Object decorate(final ClassLoader loader, final Field field) {
        if (Element.class.isAssignableFrom(field.getType())) {
            return decorateElement(loader, field);
        }
        if (isDecoratedList(field, Element.class)) {
            return decorateElementList(loader, field);
        }
        if (Component.class.isAssignableFrom(field.getType())) {
            return decorateComponent(loader, field);
        }
        if (isDecoratedList(field, Component.class)) {
            return decorateComponentList(loader, field);
        }
        return super.decorate(loader, field);
    }

    @Override
    protected WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        final InvocationHandler handler = new ElementHandler(locator);
        return (WebElement) Proxy
            .newProxyInstance(loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);
    }

    private Object decorateElement(final ClassLoader loader, final Field field) {
        final WebElement wrappedElement = proxyForLocator(loader, createLocator(field));
        return elementFactory.create((Class<? extends Element>) field.getType(), wrappedElement);
    }

    private Object decorateElementList(final ClassLoader loader, final Field field) {
        Class<? extends Element> clazz = (Class<? extends Element>) (((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
        return proxyForListElement(loader, createLocator(field), clazz, isStatic(field));
    }

    private Object decorateComponent(final ClassLoader loader, final Field field) {
        final WebElement wrappedElement = proxyForLocator(loader, createLocator(field));
        return componentFactory.create((Class<? extends Component>) field.getType(), wrappedElement);
    }

    private Object decorateComponentList(final ClassLoader loader, final Field field) {
        Class<? extends Component> clazz = (Class<? extends Component>) (((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
        return proxyForListComponent(loader, createLocator(field), clazz, isStatic(field));
    }

    private <T extends Element> List<T> proxyForListElement(ClassLoader loader, ElementLocator locator, Class<T> clazz, boolean isStatic) {
        final InvocationHandler handler = new ListHandler(locator, loader, clazz, elementFactory, isStatic);
        return (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }

    private <T extends Component> List<T> proxyForListComponent(ClassLoader loader, ElementLocator locator, Class<T> clazz, boolean isStatic) {
        final InvocationHandler handler = new ListHandler(locator, loader, clazz, componentFactory, isStatic);
        return (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }

    private ElementLocator createLocator(final Field field) {
        return factory.createLocator(field);
    }

    private boolean isDecoratedList(Field field, Class<?> paramType) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }

        Class<?> clazzList = (Class<?>) (((ParameterizedType) genericType).getActualTypeArguments()[0]);

        if (!paramType.isAssignableFrom(clazzList)) {
            return false;
        }

        if (field.getAnnotation(FindBy.class) == null &&
            field.getAnnotation(FindBys.class) == null &&
            field.getAnnotation(FindAll.class) == null) {
            return false;
        }
        return true;
    }

    private boolean isStatic(Field field) {
//        return field.isAnnotationPresent(Static.class);
        return false;
    }
}