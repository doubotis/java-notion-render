# java-notion-render
 Rendering framework for Notion

This project serves as a modulable rendering engine for Notion Block objects (https://www.notion.so/).

Comes with a sample web app that serves files from the web app resources folder, in form of JSON files, extracted from the private Notion API.
IF you want to extract these JSON files from the private notion API, you can see this kotlin library : https://github.com/notionsdk/notion-sdk-kotlin

## Usage

First, we need to retreive a NotionRecordMap based on a JSON file.

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
