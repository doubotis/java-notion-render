package be.doubotis.notion.render.theme.notion;

import be.doubotis.notion.entities.NotionBlock;
import be.doubotis.notion.render.RenderContext;
import be.doubotis.notion.render.theme.notion.renders.*;
import be.doubotis.notion.render.BlockRender;
import be.doubotis.notion.render.BlockRenderFactory;
import be.doubotis.notion.render.engine.DOMBuilder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NotionRenderContext implements RenderContext {

    private DOMBuilder mDOM;
    private Map<String, NotionBlock> mBlocks;
    private ArrayList<String> mPrintedIDs;
    private Map<String, BlockRender> mRenders;
    private String mPageID;
    private int mRenderingStep;

    public NotionRenderContext(Map<String, NotionBlock> blocks) {
        mBlocks = blocks;
        mPrintedIDs = new ArrayList<>();
        mRenders = new HashMap<>();
        mRenderingStep = 0;
        mDOM = new DOMBuilder();
    }

    @Override
    public Map<String, NotionBlock> getBlocks() {
        return mBlocks;
    }

    @Override
    public Set<String> getKeySet() {
        return mBlocks.keySet();
    }

    @Override
    public int getRenderingStep() {
        return mRenderingStep;
    }


    @Override
    public String getPageID() {
        return mPageID;
    }

    @Override
    public void flagAsRendered(String id)
    {
        System.out.println("Flagged " + id + " as rendered");
        mPrintedIDs.add(id);
    }

    public void render(PrintWriter pw) {

        mDOM.clear();

        // Get the page ID.
        String firstId = mBlocks.keySet().iterator().next();
        mPageID = firstId;
        System.out.println("PageID: " + mPageID);

        // Browse the blocks and ask a rendering.
        mRenderingStep = 0;
        for (String key : mBlocks.keySet()) {
            NotionBlock nb = mBlocks.get(key);

            // If this block is already printed, we skip it.
            if (mPrintedIDs.contains(key)) {
                mRenderingStep++;
                continue;
            }

            doRender(mDOM, key, nb);
            mRenderingStep++;
        }

        // After the rendering is complete, we execute the doAfter() logic on every blocks.
        for (String key : mBlocks.keySet()) {
            NotionBlock nb = mBlocks.get(key);
            doAfter(mDOM, key, nb);
        }

        // Process is complete, write into the printwriter.
        mDOM.writeTo(pw);
    }

    public void doRender(DOMBuilder dom, String blockId, NotionBlock nb) {
        System.out.println(blockId + " | " + nb.toString());
        BlockRender render = getRender(blockId, nb);
        if (render != null) {
            render.render(dom, this, blockId, nb);
        }
    }

    public void doAfter(DOMBuilder dom, String blockId, NotionBlock nb) {
        BlockRender render = getRender(blockId, nb);
        if (render != null) {
            render.doAfter(dom, this, blockId, nb);
        }
    }

    public synchronized BlockRender getRender(String id, NotionBlock nb) {

        String blockType = nb.getValue().getType();

        // Loop inside already instantiated renders.
        BlockRender br = mRenders.get(blockType);
        if (br == null) {
            br = instantiateRender(blockType);

            if (br != null) {
                mRenders.put(blockType, br);
            }
        }

        // Return the wanted render.
        return br;
    }

    private BlockRender instantiateRender(String blockType)
    {
        if (blockType.equals(BlockRenderFactory.TYPE_PAGE)) {
            return new BlockPageRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_TEXT)) {
            return new BlockTextRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_IMAGE)) {
            return new BlockImageRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_HEADER)) {
            return new BlockHeaderRender(2);
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_SUB_HEADER)) {
            return new BlockHeaderRender(3);
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_SUB_SUB_HEADER)) {
            return new BlockHeaderRender(4);
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_BULLETED_LIST)) {
            return new BlockBulletedListRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_NUMBERED_LIST)) {
            return new BlockNumberedListRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_QUOTE)) {
            return new BlockQuoteRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_TODO)) {
            return new BlockTodoRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_BOOKMARK)) {
            return new BlockBookmarkRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_COLUMN_LIST)) {
            return new BlockColumnListRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_COLUMN)) {
            return new BlockColumnRender();
        }
        else if (blockType.equals(BlockRenderFactory.TYPE_CALLOUT)) {
            return new BlockCalloutRender();
        }
        else {
            System.err.println("Block type not recognized: " + blockType);
            return null;
        }
    }


}
