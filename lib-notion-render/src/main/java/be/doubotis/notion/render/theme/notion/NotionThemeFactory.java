package be.doubotis.notion.render.theme.notion;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.BlockRenderFactory;

import java.io.*;
import java.util.Map;

public class NotionThemeFactory implements BlockRenderFactory {

    public NotionThemeFactory() {

    }

    public synchronized void printCascadingStylesheet(PrintWriter pw)
    {
        try {
            InputStream is = getClass().getResource("/default-theme.css").openStream();

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int length; (length = is.read(buffer)) != -1; ) {
                result.write(buffer, 0, length);
            }
            // StandardCharsets.UTF_8.name() > JDK 7
            String css = result.toString("UTF-8");
            pw.print(css);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public synchronized void printHTMLContent(PrintWriter pw, Map<String, NotionBlock> blocks)
    {
        NotionRenderContext ctx = new NotionRenderContext(blocks);
        ctx.render(pw);
    }

}
