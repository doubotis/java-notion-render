package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.entities.NotionProperties;
import be.doubotis.notion.render.BlockRenderFactory;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class BlockPageRender extends BlockHeaderRender {

    static final SpanRender SPAN_RENDER = new SpanRender();

    public BlockPageRender() {
        super(1);
    }

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        List titleEl = (List) nb.getValue().getProperties().get("title");
        String parentId = nb.getValue().getParentId();
        String parentTable = nb.getValue().getParentTable();

        if (blockId.equals(context.getPageID())) {
            // This is the main title header block.
            Element div = dom.createElement( "h1", blockId);
            div.html(SPAN_RENDER.renderText(titleEl));
            insertIntoDocument(dom, context, null, div);
        }
        else
        {
            boolean mustBeBeDisplayAsSubPage = mustBeBeDisplayAsSubPage(context, parentTable, parentId);
            if (mustBeBeDisplayAsSubPage) {
                // Special case, that means this is a component of the current page.
                Element div = dom.createElement("div", null);
                div.attr("style", "padding-top: 3px; padding-bottom: 3px;");
                Element a = dom.createElement("a", blockId);
                a.attr("href", "/NotionServlet?pageid=" + blockId);
                a.addClass("page-link");
                a.html("<i class=\"far fa-file-alt\"></i>" + SPAN_RENDER.renderText(titleEl));
                div.appendChild(a);
                dom.getDocument().appendChild(div);
            } else {
                // In every other case, this is a single link to another page.
                // We ignore generation, because no div must be generated.
            }
        }

        context.flagAsRendered(blockId);

        // 1442b728-5810-4cb5-859c-f968c33e478e | NotionBlock(role=reader, value=NotionBlockValue(alive=true, version=74, type=page, viewIds=null, collectionId=null, createdTime=1618560420000, lastEditedTime=1618560660000, parentId=625aced5-05c3-4f8f-b943-2bc75dc80833, parentTable=space, ignoreBlockCount=null, createdByTable=notion_user, createdById=dac07695-f684-4a5d-b178-253aac4aa03b, lastEditedByTable=notion_user, lastEditedById=dac07695-f684-4a5d-b178-253aac4aa03b, shardId=null, spaceId=625aced5-05c3-4f8f-b943-2bc75dc80833, properties={title=[["Tests de formatage"]]}))
        // b80ac4ef-ed6c-4002-b899-15e28c11e00d | NotionBlock(role=reader, value=NotionBlockValue(alive=true, version=190, type=page, viewIds=null, collectionId=null, createdTime=1569063780000, lastEditedTime=1618560480000, parentId=625aced5-05c3-4f8f-b943-2bc75dc80833, parentTable=space, ignoreBlockCount=null, createdByTable=notion_user, createdById=dac07695-f684-4a5d-b178-253aac4aa03b, lastEditedByTable=notion_user, lastEditedById=dac07695-f684-4a5d-b178-253aac4aa03b, shardId=null, spaceId=625aced5-05c3-4f8f-b943-2bc75dc80833, properties={title=[["Idées MyGDPR"]]}))
    }

    private boolean mustBeBeDisplayAsSubPage(RenderContext context, String parentTable, String parentId) {

        boolean isParentTypeBlock = parentTable.equals("block");
        boolean sameParentPageId = parentId.equals(context.getPageID());
        boolean sameParentOfPage = false;

        NotionBlock parentNotionBlock = context.getBlocks().get(parentId);
        if (parentNotionBlock != null && !parentNotionBlock.getValue().getType().equals(BlockRenderFactory.TYPE_PAGE)) {
            sameParentOfPage = true;
        }

        return (isParentTypeBlock && (sameParentPageId || sameParentOfPage));
    }

    @Override
    public void doAfter(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        List titleEl = (List) nb.getValue().getProperties().get("title");
        String parentTable = nb.getValue().getParentTable();

        //if (!blockId.equals(context.getPageID()) && parentTable.equals("space")) {
            // This is link to another page.
            // Let's find some links inside the page and insert the right name.
            Elements elements = dom.querySelectorAll("a[data-binding=\"" + blockId + "\"]");
            for (Element a : elements) {
                a.html("<i class=\"fas fa-link\"></i>" + SPAN_RENDER.renderText(titleEl));
            }
        //}
    }
}
