package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.*;

public class BlockNumberedListRender extends BlockBulletedListRender {

    static final SpanRender SPAN_RENDER = new SpanRender();

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        Element ol = dom.createElement("ol", null);
        ol.attr("data-first-id", blockId);

        // We need to browse all elements below to find the elements that are bound to this one.
        LinkedHashMap<String, NotionBlock> lines = obtainLines(context, blockId, nb);
        context.flagAsRendered(blockId);

        dom.getDocument().appendChild(ol);
        System.out.println("We found " + lines.size() + " sub elements to this numbered list");

        Iterator<String> linesIterator = lines.keySet().iterator();
        while (linesIterator.hasNext()) {
            String id = linesIterator.next();
            NotionBlock block = lines.get(id);
            printNumberedListLine(dom, ol, id, block);
            context.flagAsRendered(id);
        }

        String parentId = nb.getValue().getParentId();
        insertIntoDocument(dom, context, parentId, ol);
    }

    private void printNumberedListLine(DOMBuilder dom, Element node, String id, NotionBlock nb) {
        List titleEl = (List) nb.getValue().getProperties().get("title");

        Element li = dom.createElement("li", id);
        li.html(SPAN_RENDER.renderText(titleEl));
        node.appendChild(li);
    }
}
