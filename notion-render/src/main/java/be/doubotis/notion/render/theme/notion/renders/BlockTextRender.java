package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.utils.NotionBlockUtils;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockTextRender extends BlockBaseRender {

    static final SpanRender SPAN_RENDER = new SpanRender();

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        List titleEl = (List) NotionBlockUtils.getProperty(nb, "title");

        Element div = dom.createElement( "p", blockId);
        div.html(SPAN_RENDER.renderText(titleEl));

        String parentId = nb.getValue().getParentId();
        if (parentId.equals(context.getPageID())) {
            dom.getDocument().appendChild(div);
        } else {
            Element selDiv = dom.querySelector("div#" + parentId);
            selDiv.appendChild(div);
        }
        context.flagAsRendered(blockId);
    }
}
