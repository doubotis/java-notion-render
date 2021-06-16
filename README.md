# java-notion-render
 Rendering framework for Notion

This project serves as a modulable rendering engine for Notion Block objects (https://www.notion.so/).

To get this to work, you'll need resources files, in form of JSON files, extracted from the private Notion API.
If you want to extract these JSON files from the private notion API, you can see this kotlin library : https://github.com/notionsdk/notion-sdk-kotlin

This project comes with :
* A sample web app that serves files from the web app resources folder.
* A console app that get files from an input folder and output a Pdf file.

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

### Console App to output Pdf file
Command line parameters :
```
-d <input directory> -o <output file>
```

## Advanced usage

You can also rewrite the theme factories to include your own behavior, CSS classes, etc.
For this, you have to create a subclass of the `NotionThemeFactory` class. In some cases, you'll want to implement `RenderContext` interface also.
Please see the `be.doubotis.notion.render.theme.notion` package for more information.

## Known caveats

Some well-known caveats :
* Datatables are not managed
* Tabs are not managed
