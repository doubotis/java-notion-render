package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.NotionRenderContext;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.*;

public class BlockBulletedListRender extends BlockBaseRender {

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        NotionRenderContext notionContext = (NotionRenderContext) context;

        Element ul = dom.createElement("ul", null);
        ul.attr("data-first-id", blockId);

        // We need to browse all elements below to find the elements that are bound to this one.
        LinkedHashMap<String, NotionBlock> lines = obtainLines(context, blockId, nb);
        context.flagAsRendered(blockId);

        dom.getDocument().appendChild(ul);
        System.out.println("We found " + lines.size() + " sub elements to this numbered list");

        Iterator<String> linesIterator = lines.keySet().iterator();
        while (linesIterator.hasNext()) {
            String id = linesIterator.next();
            NotionBlock block = lines.get(id);
            printNumberedListLine(dom, notionContext, ul, id, block);
            context.flagAsRendered(id);
        }

        String parentId = nb.getValue().getParentId();
        insertIntoDocument(dom, context, parentId, ul);
    }

    protected LinkedHashMap<String, NotionBlock> obtainLines(RenderContext context, String blockId, NotionBlock nb) {
        // Get the sorted list of blocks.
        Set<String> setKey = context.getKeySet();
        List<String> arrayKeys = new ArrayList<>(setKey);
        int currentStep = context.getRenderingStep();

        LinkedHashMap<String, NotionBlock> lines = new LinkedHashMap<>();
        lines.put(blockId, nb);

        String initialParentId = extractParentId(nb);
        String currentParentId;
        for (int i=currentStep+1; i < context.getBlocks().size(); i++) {
            NotionBlock subBlock = context.getBlocks().get(arrayKeys.get(i));
            currentParentId = extractParentId(subBlock);

            if (currentParentId.equals(initialParentId) && subBlock.getValue().getType().equals(nb.getValue().getType())) {
                // This one is on the same parent.
                lines.put(arrayKeys.get(i), subBlock);
            } else if (lines.get(currentParentId) != null) {
                // This one is a child of one of the <li> elements. We do not break, but do not add too.
            } else {
                break;
            }
        }

        return lines;
    }

    private String extractParentId(NotionBlock nb)
    {
        return nb.getValue().getParentId();
    }

    private void printNumberedListLine(DOMBuilder dom, NotionRenderContext context, Element node, String id, NotionBlock nb) {
        List titleEl = (List) nb.getValue().getProperties().get("title");

        Element li = dom.createElement("li", id);
        li.html(context.renderSpan(titleEl));
        node.appendChild(li);
    }
}
