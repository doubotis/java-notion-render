# java-notion-render
 Rendering framework for Notion

This project serves as a modulable rendering engine for Notion Block objects (https://www.notion.so/).

Comes with a sample web app that serves files from the web app resources folder, in form of JSON files, extracted from the private Notion API.
IF you want to extract these JSON files from the private notion API, you can see this kotlin library : https://github.com/notionsdk/notion-sdk-kotlin

## Basic usage

First, we need to retreive a `NotionRecordMap` based on a JSON file.

```
File f = new File("<Your filepath>" + pageId.replace("-", "") + ".json");
ObjectMapper om = new ObjectMapper();
NotionRecordMap nrm = om.readValue(f, NotionRecordMap.class);
Map<String, NotionBlock> blocks = nrm.getBlocks();
```

Then, we can ask for a rendering of the blocks.
```
PrintWriter pw = response.getWriter();

// Render the blocks.
BlockRenderFactory factory = new NotionThemeFactory();
factory.printHTMLContent(pw, blocks);
```

## Advanced usage

You can also rewrite the theme factories to include your own behavior, CSS classes, etc.
For this, you need to create a subclass of the `NotionThemeFactory` class. You'll need possibly to implement `RenderContext` interface.
Please see the `be.doubotis.notion.render.theme.notion` package for more information.

## Examples

The web project showcase how you can produce HTML contents based on Notion API objects.

## Known caveats

Some well-known caveats :
* Datatables are not managed
* Tabs are not managed
