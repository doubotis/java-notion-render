package be.doubotis.notion.pdf;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.entities.NotionRecordMap;
import be.doubotis.notion.render.theme.notion.NotionThemeFactory;
import be.doubotis.notion.render.theme.pdf.PdfThemeFactory;
import be.doubotis.notion.utils.HtmlPageBuilder;
import be.doubotis.notion.utils.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

public class PdfBuilder {

    private String mContents = "";
    private ArrayList<File> mFiles;

    public PdfBuilder() {
        mFiles = new ArrayList<>();
    }

    public void addInputFile(File inputFile) {
        mFiles.add(inputFile);
    }

    public void build() throws IOException {

        String completeHtml = "";

        HtmlPageBuilder pageBuilder = new HtmlPageBuilder();

        PdfThemeFactory themeFactory = new PdfThemeFactory();

        for (int i=0; i < mFiles.size(); i++) {
            String json = IOUtils.readString(mFiles.get(i).getAbsolutePath());

            ObjectMapper om = new ObjectMapper();
            NotionRecordMap nrm = om.readValue(json, NotionRecordMap.class);
            Map<String, NotionBlock> blocks = nrm.getBlocks();

            StringWriter swHtml = new StringWriter();
            PrintWriter pwHtml = new PrintWriter(swHtml);
            themeFactory.printHTMLContent(pwHtml, blocks);
            pwHtml.flush();
            String html = swHtml.toString();
            pageBuilder.addHtmlContent(html);
        }

        // Prepares a String print writer.
        StringWriter swCSS = new StringWriter();
        PrintWriter pwCSS = new PrintWriter(swCSS);
        themeFactory.printCascadingStylesheet(pwCSS);
        String stylesheetContent = swCSS.toString();
        pageBuilder.addStylesheet(stylesheetContent);

        mContents = pageBuilder.getDocument().html();
    }

    public void writeToFile(File outputFile) throws IOException {

        PdfWriter pdfWriter = new PdfWriter(outputFile);

        PdfDocument pdf = new PdfDocument(pdfWriter);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new TextFooterEventHandler(36,36,36,36));

        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri("");
        Document doc = HtmlConverter.convertToDocument(mContents, pdf, properties);
        doc.close();

        System.out.println("Pdf file generated: " + outputFile.getAbsolutePath());
    }
}
