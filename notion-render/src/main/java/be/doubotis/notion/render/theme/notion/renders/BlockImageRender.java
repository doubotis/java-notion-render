package be.doubotis.notion.render.theme.notion.renders;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.entities.Primitive;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.engine.DOMBuilder;
import be.doubotis.notion.render.theme.notion.SpanRender;
import org.jsoup.nodes.Element;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class BlockImageRender extends BlockBaseRender {

    @Override
    public void render(DOMBuilder dom, RenderContext context, String blockId, NotionBlock nb) {
        List sourceEl = (List) nb.getValue().getProperties().get("source");
        Map<String, Object> format = nb.getValue().getFormat();

        if (sourceEl != null) {
            List sourceComponentEl = (List) sourceEl.get(0);
            Primitive sourcePrimitive = Primitive.from(sourceComponentEl.get(0));
            System.out.println("Source: " + sourcePrimitive.getContent());
            String imgSrc = buildFinalImageSrc(sourcePrimitive.getContent(), blockId);

            Element img = dom.createElement("img", blockId);
            img.addClass("responsive");
            img.attr("src", imgSrc);

            // If we got a format, we need to apply it to the img tag.
            if (format != null) {
                String styleString = generateStyleFromFormat(format);
                img.attr("style", styleString);
            }

            String parentId = nb.getValue().getParentId();
            insertIntoDocument(dom, context, parentId, img);
        }


        context.flagAsRendered(blockId);
    }

    private String buildFinalImageSrc(String src, String blockId) {
        if (src.contains("secure.notion-static.com")) {
            // In that case, we need to wrap the request.
            String destSrc = "https://notion.so/image/" + URLEncoder.encode(src) + "?table=block&id=" + blockId;
            return destSrc;
        } else {
            return src;
        }
    }

    private String generateStyleFromFormat(Map<String,Object> format)
    {
        StringBuilder sb = new StringBuilder();

        Primitive blockWithPrimitive = Primitive.from(format.get("block_width"));
        if (blockWithPrimitive != null) {
            String blockWidthString = Integer.parseInt(blockWithPrimitive.getContent()) + "px";
            sb.append("width: " + blockWidthString + ";");
        }

        //TODO: Manage these properties as well.
        //block_aspect_ratio: 0.75
        //block_full_width: false
        //block_height: 320
        //block_page_width: false
        //block_preserve_scale: true

        return sb.toString();
    }
}
