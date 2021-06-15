package be.doubotis.notion.render.utils;

import be.doubotis.notion.entities.NotionBlock;

public class NotionBlockUtils {

    public static Object getProperty(NotionBlock block, String property) {
        if (block.getValue().getProperties() == null) return null;
        Object propertyEl = block.getValue().getProperties().get(property);
        return propertyEl;
    }
}
