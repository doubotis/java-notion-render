package be.doubotis.notion.render.engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.PrintWriter;

public class DOMBuilder {

    private Document mDocument = null;

    public DOMBuilder() {
        clear();
    }

    private void prepare() {

    }

    public Element querySelector(String cssQuery) {
        Elements elements = mDocument.select(cssQuery);
        if (!elements.isEmpty()) return elements.get(0);
        return null;
    }

    public Elements querySelectorAll(String cssQuery) {
        Elements elements = mDocument.select(cssQuery);
        return elements;
    }

    public Element createElement(String tagName, String id) {
        Element el = mDocument.createElement(tagName);
        if (id != null) {
            el.attr("id", id);
        }
        return el;
    }

    public Element appendElement(Element node, String tagName, String id) {
        Element el = createElement(tagName, id);
        node.appendChild(el);
        return el;
    }

    public Element appendElementFromQuery(String cssQuery, String  tagName, String id) {
        Element el = createElement(tagName, id);
        Element node = querySelector(cssQuery);
        node.appendChild(el);
        return el;
    }

    public Document getDocument() {
        return mDocument;
    }

    public void writeTo(PrintWriter pw)
    {
        mDocument.select("body").remove();
        mDocument.select("head").remove();
        mDocument.select("html").remove();
        //mDocument.outputSettings().prettyPrint(true).escapeMode(Entities.EscapeMode.xhtml).charset("UTF-8");
        String content = mDocument.outerHtml();
        pw.print(content);
    }

    public void clear() {
        mDocument = Jsoup.parse("");
    }
}
