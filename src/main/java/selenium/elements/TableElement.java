package selenium.elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Tsovak_Sahakyan.
 */
public class TableElement extends BaseElement {
    private List<TableRowElement> rows;
    private TableRowElement header;

    public TableElement(WebElement avatar) {
        super(avatar);
    }

    public TableElement(By by) {
        super(by);
    }


    public int size() {
        return 1 + getRows().size();
    }

    public TableRowElement getHeader() {
        if (header == null) {
            header = getRows().get(0);
        }
        return header;
    }

    public List<TableRowElement> getRows() {
        if (rows == null) {
            rows = createTableRowElements(By.tagName("tr"));
        }
        return rows;
    }

    public TableRowElement getRow(int index) {
        return getRows().get(index);
    }

    public List<Map<String, String>> getHash() {
        Document doc = Jsoup.parse(this.getAttribute("outerHTML"), "UTF-8");
        List<String> headerStrings = doc.select("thead > tr > td").stream()
            .map(org.jsoup.nodes.Element::text)
            .collect(Collectors.toList());
        List<Map<String, String>> listHash = new ArrayList<>();
        for(org.jsoup.nodes.Element row: doc.select("tbody > tr")) {
            HashMap<String, String> rowHash = new HashMap<>();
            Elements columns = row.select("td");
            for(int i = 0; i < columns.size(); i++) {
                rowHash.put(headerStrings.get(i), columns.get(i).text());
            }
            listHash.add(rowHash);
        }
        return listHash;
    }
}