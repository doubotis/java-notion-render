package be.doubotis.notion.render;

import be.doubotis.notion.entities.NotionBlock;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;

/** Interface that allows the rendering of blocks by modular way. */
public interface BlockRenderFactory {

    public static final String TYPE_PAGE = "page";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_HEADER = "header";
    public static final String TYPE_SUB_HEADER = "sub_header";
    public static final String TYPE_SUB_SUB_HEADER = "sub_sub_header";
    public static final String TYPE_BULLETED_LIST = "bulleted_list";
    public static final String TYPE_NUMBERED_LIST = "numbered_list";
    public static final String TYPE_QUOTE = "quote";
    public static final String TYPE_TODO = "to_do";
    public static final String TYPE_BOOKMARK = "bookmark";
    public static final String TYPE_COLUMN_LIST = "column_list";
    public static final String TYPE_COLUMN = "column";
    public static final String TYPE_CALLOUT = "callout";

    void printCascadingStylesheet(PrintWriter pw);
    void printHTMLContent(PrintWriter pw, Map<String, NotionBlock> blocks);
}
