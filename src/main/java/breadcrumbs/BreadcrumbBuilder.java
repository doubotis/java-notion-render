package breadcrumbs;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.BlockRenderFactory;
import be.doubotis.notion.render.theme.notion.SpanRender;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class BreadcrumbBuilder {

    public void printHTMLContent(PrintWriter pw, Map<String, NotionBlock> blocks) {

        ArrayList<NotionBlock> pageBlocks = new ArrayList<>();
        ArrayList<String> pageIds = new ArrayList<>();

        // Loop inside blocks that are pages.
        for (String key : blocks.keySet()) {
            NotionBlock nb = blocks.get(key);
            if (nb.getValue().getType().equals(BlockRenderFactory.TYPE_PAGE)) {
                pageBlocks.add(nb);
                pageIds.add(key);
            } else {
                // No more page headers, skip it.
                break;
            }
        }

        Collections.reverse(pageBlocks);
        Collections.reverse(pageIds);

        pw.println("<div class=\"breadcrumb\"><ul>");
        int i=0;
        for (NotionBlock nb : pageBlocks) {
            String pageId = pageIds.get(i);
            Object titleEl = nb.getValue().getProperties().get("title");
            String html = new SpanRender().renderText(titleEl);

            pw.print("<li><a href=\"" + "/NotionServlet?pageid=" + pageId + "\">" + html + "</a></li>");
            i++;
        }
        pw.println("</ul></div>");
    }
}

