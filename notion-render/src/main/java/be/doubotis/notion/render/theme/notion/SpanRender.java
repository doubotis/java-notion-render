package be.doubotis.notion.render.theme.notion;

import be.doubotis.notion.entities.Primitive;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.TextRender;

import java.util.*;

public class SpanRender implements TextRender {

    private RenderContext mContext;

    public SpanRender(RenderContext context) {
        mContext = context;
    }

    @Override
    public String renderText(Object object) {
        List<Object> spans = (List<Object>)object;
        if (spans == null) return "";

        StringBuilder sb = new StringBuilder();
        List<Object> titleEl = spans;
        if (titleEl instanceof List) {
            List<Object> title = (List<Object>) titleEl;
            for (Object titleComponentEl : title) {
                List span = (List) titleComponentEl;
                boolean hasStyle = span.size() > 1;
                if (hasStyle) {
                    // Opener brackets.
                    List stylesMaster = (List) span.get(1);
                    ArrayList stylesList = extractFromJsonArray(stylesMaster);
                    for (Object styleEl : stylesList) {
                        List style = (List) styleEl;
                        String spanHtml = buildSpanHTML(false, style);
                        sb.append(spanHtml);
                    }
                }

                // Print the span content string.
                Map<String,String> htmlPrimitive = (Map<String,String>) span.get(0);
                String html = (String) htmlPrimitive.get("content");
                html = html.replace("\n","<br>");
                sb.append(html);

                if (hasStyle) {
                    // Closer brackets.
                    List stylesMaster = (List) span.get(1);
                    ArrayList stylesList = extractFromJsonArray(stylesMaster);
                    Collections.reverse(stylesList);
                    for (Object styleEl : stylesList) {
                        List style = (List) styleEl;
                        String spanHtml = buildSpanHTML(true, style);
                        sb.append(spanHtml);
                    }
                }
            }
        }

        return sb.toString();

    }

    private ArrayList extractFromJsonArray(List array) {
        ArrayList data = new ArrayList();
        if (array != null) {
            for (int i=0;i<array.size();i++){
                data.add(array.get(i));
            }
        }
        return data;
    }

    private String buildSpanHTML(boolean closure, List tags) {

        Primitive tagPrimitive = Primitive.from(tags.get(0));
        String tag = tagPrimitive.getContent();
        if (tag.equals("p")) {
            // Specific case.
            if (!closure) {
                Primitive idPrimitive = Primitive.from(tags.get(1));
                String id = idPrimitive.getContent();
                String url = mContext.buildLinkUrl(id);
                return "<a class=\"page-link\" href=\"" + url + "\" data-binding=\"" + id + "\">";
            } else {
                return "</a>";
            }
        }
        else if (tag.equals("a")) {
            // Specific case.
            if (!closure) {
                Primitive urlPrimitive = Primitive.from(tags.get(1));
                String url = urlPrimitive.getContent();
                return "<a class=\"web-link\" href=\"" + url + "\">";
            } else {
                return "</a>";
            }
        }
        else
        {
            List<String> handlesCases = Arrays.asList(new String[] { "_", "b", "s", "i", "h" });
            // Span case.
            if (!closure) {
                if (tag.equals("_")) {
                    return "<span style=\"text-decoration: underline;\">";
                } else if (tag.equals("b")) {
                    return "<span style=\"font-weight: bold;\">";
                } else if (tag.equals("s")) {
                    return "<span style=\"text-decoration: line-through;\">";
                } else if (tag.equals("i")) {
                    return "<span style=\"font-style: italic;\">";
                } else if (tag.equals("h")) {
                    Primitive colorPrimitive = Primitive.from(tags.get(1));
                    String color = colorPrimitive.getContent();
                    return "<span style=\"color: " + color + ";\">";
                } else {
                    return "";
                }
            } else {
                if (handlesCases.contains(tag)) {
                    return "</span>";
                } else {
                    return "";
                }
            }
        }
    }
}
