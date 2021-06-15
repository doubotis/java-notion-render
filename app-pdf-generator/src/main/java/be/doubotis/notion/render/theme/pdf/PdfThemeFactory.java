
package be.doubotis.notion.render.theme.pdf;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.theme.notion.NotionThemeFactory;

import java.io.PrintWriter;
import java.util.Map;

/**
 *
 * @author cbrasseur
 */
public class PdfThemeFactory extends NotionThemeFactory {

    public PdfThemeFactory() {

    }

    @Override
    public synchronized void printHTMLContent(PrintWriter pw, Map<String, NotionBlock> blocks)
    {
        PdfRenderContext ctx = new PdfRenderContext(blocks);
        ctx.render(pw);
    }
}
