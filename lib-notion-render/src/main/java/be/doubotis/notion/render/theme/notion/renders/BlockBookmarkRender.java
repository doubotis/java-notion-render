package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockBookmarkRender extends BlockBaseRender {

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {

        List linkEl = (List) nb.getValue().getProperties().get("link");
        List titleEl = (List) nb.getValue().getProperties().get("title");
        List descriptionEl = (List) nb.getValue().getProperties().get("description");

        Element div = dom.createElement( "div", blockId);
        div.addClass("bookmark");
        String parentId = nb.getValue().getParentId();
        insertIntoDocument(dom, context, parentId, div);

        //TODO
        System.out.println(nb.toString());
        context.flagAsRendered(blockId);
    }
}
