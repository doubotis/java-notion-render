package be.doubotis.notion.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class NotionRecordMap {

    @JsonProperty("blocksMap")
    private LinkedHashMap<String, NotionBlock> blocks;

    public LinkedHashMap<String, NotionBlock> getBlocks() {
        return blocks;
    }
    public void setBlocks(LinkedHashMap<String, NotionBlock> blocks) {
        this.blocks = blocks;
    }
}
