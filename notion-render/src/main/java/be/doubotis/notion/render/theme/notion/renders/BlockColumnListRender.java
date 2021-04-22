package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

public class BlockColumnListRender extends BlockBaseRender {

    static final SpanRender SPAN_RENDER = new SpanRender();

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        Element divRow = dom.createElement("div", blockId);
        Element divFlex = dom.createElement("div", null);
        divFlex.addClass("grid-row");
        divRow.appendChild(divFlex);
        dom.getDocument().appendChild(divRow);
        context.flagAsRendered(blockId);
    }

    private String extractParentId(NotionBlock nb)
    {
        return nb.getValue().getParentId();
    }
}
