package selenium.elements;

/**
 * Created by Tsovak_Sahakyan.
 */
public interface ElementContext<T extends Element> {
    T withIframe(Element iframeElement);
    T withHover(Element hoverElement);
    T withParent(Element parentElement);
    T withAutoScrollIntoView();
}
