package be.doubotis.notion.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedHashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotionProperties extends LinkedHashMap<String, Object> {

}
