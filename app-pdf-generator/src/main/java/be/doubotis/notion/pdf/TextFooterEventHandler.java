package be.doubotis.notion.pdf;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

import java.io.IOException;

public class TextFooterEventHandler implements IEventHandler {

    private float mTopMargin;
    private float mLeftMargin;
    private float mBottomMargin;
    private float mRightMargin;

    private int mCountEvents = 0;

    public TextFooterEventHandler(float topMargin, float leftMargin, float bottomMargin, float rightMargin) {
        mTopMargin = topMargin;
        mLeftMargin = leftMargin;
        mBottomMargin = bottomMargin;
        mRightMargin = rightMargin;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;

        mCountEvents++;

        PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
        Rectangle pageSize = docEvent.getPage().getPageSize();
        canvas.beginText();

        try {
            canvas.setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA_OBLIQUE), 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        canvas.moveText(mLeftMargin, pageSize.getTop() - mTopMargin + 10)
                .showText("SityTrail Documentation - Page " + mCountEvents)
                .endText()
                .release();
        System.out.println(docEvent.toString());
    }
}
