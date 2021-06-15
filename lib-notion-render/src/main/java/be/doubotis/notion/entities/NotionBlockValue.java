package be.doubotis.notion.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotionBlockValue {

    @JsonProperty("type")
    private String type;

    @JsonProperty("properties")
    private NotionProperties properties;

    @JsonProperty("parentId")
    private String parentId;

    @JsonProperty("parentTable")
    private String parentTable;

    @JsonProperty("format")
    private Map<String, Object> format;

    @JsonProperty("alive")
    private String alive;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("createdTime")
    private Date createdTime;

    @JsonProperty("lastEditedTime")
    private Date lastEditedTime;

    @JsonProperty("createdByTable")
    private String createdByTable;

    @JsonProperty("createdById")
    private String createdById;

    @JsonProperty("lastEditedByTable")
    private String lastEditedByTable;

    @JsonProperty("lastEditedById")
    private String lastEditedById;

    @JsonProperty("spaceId")
    private String spaceId;

    @JsonProperty("viewIds")
    private ArrayList<String> viewIds;

    @JsonProperty("collectionId")
    private String collectionId;

    public String getType() {
        return type;
    }
    public void setType(String type) { this.type = type; }

    public NotionProperties getProperties() {
        return properties;
    }

    public String getParentId() {
        return parentId;
    }

    public String getParentTable() {
        return parentTable;
    }

    public Map<String, Object> getFormat() {
        return format;
    }
}
