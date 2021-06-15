package be.doubotis.notion;

import be.doubotis.notion.pdf.PdfBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static final void main(String[] args) throws Exception {

        CommandLineParser parser = new DefaultParser();
        Options opt = new Options();
        opt.addOption("d", true, "Folder of input files");
        opt.addOption("o", true, "Output file");
        opt.getOption("d").setRequired(true);
        opt.getOption("o").setRequired(true);
        CommandLine cmd = parser.parse(opt, args);

        try {

            // Get the input directory.
            String inputPath = cmd.getOptionValue("d");
            File inputDirectory = new File(inputPath);

            // Get the output file.
            String outputPath = cmd.getOptionValue("o");
            File outputFile = new File(outputPath);

            PdfBuilder builder = new PdfBuilder();

            // Browse all the files.
            File[] files = inputDirectory.listFiles();
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            for (File file : files) {
                builder.addInputFile(file);
            }

            // Build the Pdf.
            builder.build();

            // Extract the result.
            builder.writeToFile(outputFile);


        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
