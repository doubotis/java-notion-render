
import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.entities.NotionRecordMap;
import be.doubotis.notion.render.BlockRenderFactory;
import be.doubotis.notion.render.theme.notion.NotionThemeFactory;
import breadcrumbs.BreadcrumbBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "NotionServlet", urlPatterns = {"/NotionServlet"})
public class NotionServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String pageId = request.getParameter("pageid");

        // For test examples.
        File f = new File("F:\\Developer\\IdeaProjects\\page-" + pageId.replace("-", "") + ".json");
        ObjectMapper om = new ObjectMapper();
        NotionRecordMap nrm = om.readValue(f, NotionRecordMap.class);
        Map<String, NotionBlock> blocks = nrm.getBlocks();

        PrintWriter pw = response.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" />");
        pw.println("<link rel=\"stylesheet\" href=\"/css/testing.css\" />");
        pw.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css\" integrity=\"sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w==\" crossorigin=\"anonymous\" />");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<div class=\"container\">");

        // Build a breadcrumb on top of the page.
        BreadcrumbBuilder breadcrumbBuilder = new BreadcrumbBuilder();
        breadcrumbBuilder.printHTMLContent(pw, blocks);

        // Render the blocks.
        BlockRenderFactory factory = new NotionThemeFactory();
        factory.printHTMLContent(pw, blocks);

        pw.println("</div>");
        pw.println("</body>");
        pw.println("</html>");
        pw.close();

    }

}
