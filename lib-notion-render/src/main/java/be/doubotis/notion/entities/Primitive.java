package be.doubotis.notion.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.beans.Transient;
import java.util.LinkedHashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Primitive extends LinkedHashMap<String,Object> {

    private Primitive(LinkedHashMap<String,Object> data) {
        super(data);
    }

    public static Primitive from(Object primitive) {
        if (primitive == null) return null;
        return new Primitive((LinkedHashMap<String, Object>) primitive);
    }

    public String getContent() {
        return (String)get("content");
    }

    public boolean isString() {
        return (Boolean)get("isString");
    }
}
