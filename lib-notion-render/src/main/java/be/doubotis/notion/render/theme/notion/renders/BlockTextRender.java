package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.NotionRenderContext;
import be.doubotis.notion.render.utils.NotionBlockUtils;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockTextRender extends BlockBaseRender {

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        NotionRenderContext notionContext = (NotionRenderContext) context;

        List titleEl = (List) NotionBlockUtils.getProperty(nb, "title");

        Element div = dom.createElement( "p", blockId);
        div.html(notionContext.renderSpan(titleEl));

        String parentId = nb.getValue().getParentId();
        insertIntoDocument(dom, context, parentId, div);
        context.flagAsRendered(blockId);
    }
}
