package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.BlockRender;
import be.doubotis.notion.render.engine.DOMBuilder;
import org.jsoup.nodes.Element;

public abstract class BlockBaseRender implements BlockRender {

    protected void insertIntoDocument(DOMBuilder dom, RenderContext context, String parentId, Element element) {
        if (parentId == null || parentId.equals(context.getPageID())) {
            dom.getDocument().appendChild(element);
        } else {
            Element selDiv = dom.querySelector("div#" + parentId);
            if (selDiv != null) {
                selDiv.appendChild(element);
            }

            Element selLi = dom.querySelector("li#" + parentId);
            if (selLi != null) {
                selLi.appendChild(element);
            }
        }
    }

    @Override
    public void doAfter(DOMBuilder dom, RenderContext ctx, String blockId, NotionBlock nb) {
        // Do nothing by default.
    }
}
