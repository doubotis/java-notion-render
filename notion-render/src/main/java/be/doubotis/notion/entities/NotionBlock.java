package be.doubotis.notion.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class NotionBlock {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private NotionBlockValue value;
    @JsonProperty("role")
    private String role;

    public String getId() {
        return id;
    }
    public NotionBlockValue getValue() {
        return value;
    }
}
