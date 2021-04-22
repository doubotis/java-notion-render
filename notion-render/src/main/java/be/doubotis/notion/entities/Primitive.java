package be.doubotis.notion.entities;

import java.util.LinkedHashMap;

public class Primitive extends LinkedHashMap<String,String> {

    private Primitive(LinkedHashMap<String,String> data) {
        super(data);
    }

    public static Primitive from(Object primitive) {
        if (primitive == null) return null;
        return new Primitive((LinkedHashMap<String, String>) primitive);
    }

    public String getContent() {
        return get("content");
    }

    public boolean isString() {
        return Boolean.parseBoolean(get("isString"));
    }
}
