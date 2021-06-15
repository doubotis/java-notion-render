package be.doubotis.notion.render;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.engine.DOMBuilder;

public interface BlockRender {

    void render(DOMBuilder dom, RenderContext ctx, String blockId, NotionBlock nb);
    void doAfter(DOMBuilder dom, RenderContext ctx, String blockId, NotionBlock nb);
}
