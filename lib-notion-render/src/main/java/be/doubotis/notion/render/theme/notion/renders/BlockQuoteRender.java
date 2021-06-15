package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.NotionRenderContext;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockQuoteRender extends BlockBaseRender {

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        NotionRenderContext notionContext = (NotionRenderContext) context;

        List titleEl = (List) nb.getValue().getProperties().get("title");

        Element div = dom.createElement( "div", blockId);
        div.addClass("quote");
        div.html(notionContext.renderSpan(titleEl));

        String parentId = nb.getValue().getParentId();
        insertIntoDocument(dom, context, parentId, div);

        context.flagAsRendered(blockId);
    }
}
