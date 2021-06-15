package be.doubotis.notion.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlPageBuilder {

    private Document mDocument;

    public HtmlPageBuilder() {
        mDocument = Jsoup.parse("<html><head></head><body></body></html>");
    }

    public void addHtmlContent(String htmlContent) {
        Element el = mDocument.selectFirst("body");
        el.append(htmlContent);
    }

    public void addStylesheet(String stylesheetContent) {
        Element el = mDocument.selectFirst("head");
        Element styleEl = mDocument.createElement("style");
        styleEl.append(stylesheetContent);
        el.appendChild(styleEl);
    }

    public Document getDocument() {
        return mDocument;
    }
}
