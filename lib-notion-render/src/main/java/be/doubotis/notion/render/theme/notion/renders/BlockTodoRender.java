package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.NotionRenderContext;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockTodoRender extends BlockBaseRender {

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        NotionRenderContext notionContext = (NotionRenderContext) context;

        List checkedEl = (List) nb.getValue().getProperties().get("checked");
        List titleEl = (List) nb.getValue().getProperties().get("title");
        Element block;
        if (checkedEl != null) {
            List checkedComponentEl = (List) checkedEl.get(0);
            String checked = (String) checkedComponentEl.get(0);
            if (checked.equals("Yes")) {
                block = buildCheckbox(dom, notionContext, blockId, titleEl, true);
            } else {
                block = buildCheckbox(dom, notionContext, blockId, titleEl, false);
            }
        } else {
            block = buildCheckbox(dom, notionContext, blockId, titleEl, false);
        }

        String parentId = nb.getValue().getParentId();
        insertIntoDocument(dom, context, parentId, block);
        context.flagAsRendered(blockId);
    }

    private Element buildCheckbox(DOMBuilder dom, NotionRenderContext context, String blockId, List titleEl, boolean checked) {

        Element ul = dom.createElement( "ul", null);
        Element li = dom.createElement("li", blockId);
        if (checked) {
            li.html("<i class=\"far fa-check-square\"></i>" + context.renderSpan(titleEl));
        } else {
            li.html("<i class=\"far fa-square\"></i>" + context.renderSpan(titleEl));
        }
        ul.appendChild(li);
        return ul;
    }
}
