package org.melligans.deep_fake_cmd;

import org.melligans.deep_fake_cmd.errors.SourceError;
import org.melligans.deep_fake_cmd.movie_converter.ConverterEntry;

public class App {
    static String source;
    static String destination;

    public static void main(String[] args) {
        argumentSorter(args);
        if(source == null) {
            throw new SourceError("Please provide a source with the -s flag!");
        }
        if(destination == null) {
            destination = System.getProperty("user.dir");
            System.out.println(
                    "Destination is set as the current directory!" +
                    "\n\t--> " +
                            destination
            );
        }
        ConverterEntry converter = new ConverterEntry(source, destination);
    }

    private static void argumentSorter(String[] args) {
        for(int i = 0; i < args.length; i++) {
            switch(args[i]) {
                case "-s":
                    source = args[i+1];
                    break;
                case "-d":
                    destination = args[i+1];
                    break;
                case "-h":
                    printHelp();
                    break;
            }
        }
    }

    private static void printHelp() {
        System.out.println(
                "Help menu for Deep Fakery:" +
                        "\n\tFlags:" +
                        "\n\t\t-h: Help (You are here)" +
                        "\n\t\t-s: Source file to convert to frames" +
                        "\n\t\t-d: Destination directory"
        );
    }
}
