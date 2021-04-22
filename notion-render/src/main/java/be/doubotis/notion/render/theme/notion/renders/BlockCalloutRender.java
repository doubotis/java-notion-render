package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.entities.Primitive;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Map;

public class BlockCalloutRender extends BlockBaseRender {

    static final SpanRender SPAN_RENDER = new SpanRender();

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        List titleEl = (List) nb.getValue().getProperties().get("title");
        Map<String, Object> format = nb.getValue().getFormat();

        Element div = dom.createElement( "div", blockId);
        div.addClass("callout");

        if (format != null) {
            Primitive backgroundColorPrimitive = Primitive.from(format.get("block_color"));
            String backgroundColor = backgroundColorPrimitive.getContent();
            div.attr("style", "background-color: " + hexColorFromLabel(backgroundColor) + ";");
        }

        Element imgIcon = dom.createElement("img", null);
        imgIcon.addClass("icon");
        imgIcon.attr("src", "");

        Element divContent = dom.createElement("div", null);
        divContent.addClass("callout-content");
        divContent.html(SPAN_RENDER.renderText(titleEl));

        div.appendChild(imgIcon);
        div.appendChild(divContent);
        dom.getDocument().appendChild(div);

        context.flagAsRendered(blockId);
    }

    private String hexColorFromLabel(String labelColor) {
        if (labelColor.equals("gray_background")) {
            return "rgba(235, 236, 237, 0.3);";
        } else {
            return "";
        }
    }
}
