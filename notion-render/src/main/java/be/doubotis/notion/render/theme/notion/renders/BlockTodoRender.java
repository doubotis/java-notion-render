package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockTodoRender extends BlockBaseRender {

    static final SpanRender SPAN_RENDER = new SpanRender();

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {

        List checkedEl = (List) nb.getValue().getProperties().get("checked");
        List titleEl = (List) nb.getValue().getProperties().get("title");
        Element block;
        if (checkedEl != null) {
            List checkedComponentEl = (List) checkedEl.get(0);
            String checked = (String) checkedComponentEl.get(0);
            if (checked.equals("Yes")) {
                block = buildCheckbox(dom, blockId, titleEl, true);
            } else {
                block = buildCheckbox(dom, blockId, titleEl, false);
            }
        } else {
            block = buildCheckbox(dom, blockId, titleEl, false);
        }

        String parentId = nb.getValue().getParentId();
        insertIntoDocument(dom, context, parentId, block);
        context.flagAsRendered(blockId);
    }

    private Element buildCheckbox(DOMBuilder dom, String blockId, List titleEl, boolean checked) {

        Element ul = dom.createElement( "ul", null);
        Element li = dom.createElement("li", blockId);
        if (checked) {
            li.html("<i class=\"far fa-check-square\"></i>" + SPAN_RENDER.renderText(titleEl));
        } else {
            li.html("<i class=\"far fa-square\"></i>" + SPAN_RENDER.renderText(titleEl));
        }
        ul.appendChild(li);
        return ul;
    }
}
