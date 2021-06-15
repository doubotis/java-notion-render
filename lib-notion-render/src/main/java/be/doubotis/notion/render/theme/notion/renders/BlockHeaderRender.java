package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.NotionRenderContext;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockHeaderRender extends BlockBaseRender {

    private int mLevel;

    public BlockHeaderRender(int level) {
        mLevel = level;
    }

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        NotionRenderContext notionContext = (NotionRenderContext) context;

        List titleEl = (List) nb.getValue().getProperties().get("title");

        Element div = dom.createElement( "h" + mLevel, blockId);
        div.html(notionContext.renderSpan(titleEl));
        dom.getDocument().appendChild(div);

        context.flagAsRendered(blockId);
    }
}
