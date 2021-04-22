package be.doubotis.notion.render;

import be.doubotis.notion.entities.NotionBlock;

import java.util.Map;
import java.util.Set;

public interface RenderContext {

    void flagAsRendered(String id);
    Map<String, NotionBlock> getBlocks();
    int getRenderingStep();
    Set<String> getKeySet();
    String getPageID();
}
