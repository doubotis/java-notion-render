package be.doubotis.notion.render;

import be.doubotis.notion.entities.NotionBlock;

import java.util.Map;
import java.util.Set;

/* Serves as a context for the rendering. */
public interface RenderContext {

    /** Flag a block id as rendered. */
    void flagAsRendered(String id);

    /** Returns the list of blocks that must be rendered. */
    Map<String, NotionBlock> getBlocks();

    /** Returns the rendering step for this context. */
    int getRenderingStep();

    /** Returns the block key set. */
    Set<String> getKeySet();

    /** Returns the main page id. */
    String getPageID();

    /** Build an internal link url. */
    String buildLinkUrl(String pageId);
}
