
package be.doubotis.notion.render.theme.pdf;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.BlockRender;
import be.doubotis.notion.render.theme.notion.NotionRenderContext;

import java.util.Map;

/**
 *
 * @author cbrasseur
 */
public class PdfRenderContext extends NotionRenderContext {

    public PdfRenderContext(Map<String, NotionBlock> blocks) {
        super(blocks);
    }

    @Override
    public String buildLinkUrl(String pageId) {
        return "#" + pageId;
    }

    @Override
    protected BlockRender instantiateRender(String blockType) {
        return super.instantiateRender(blockType);
    }
    
}
