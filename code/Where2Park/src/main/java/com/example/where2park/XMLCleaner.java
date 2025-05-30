package com.example.where2park;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class XMLCleaner {

    /**
     * Removes blank lines from the given XML file and rewrites it.
     * @param filePath Path to the XML file
     */
    public static void removeBlankLines(String filePath) {
        try {
            File inputFile = new File(filePath);

            // Read all lines and filter out empty or whitespace-only lines
            String cleanedContent = Files.lines(Paths.get(filePath))
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.joining(System.lineSeparator()));

            // Overwrite the original file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
                writer.write(cleanedContent);
            }

            System.out.println("Blank lines removed from: " + filePath);

        } catch (IOException e) {
            System.err.println("Error processing XML file: " + e.getMessage());
        }
    }

    // For manual testing
    public static void main(String[] args) {
        String xmlPath = "src/main/data/parking.xml"; // Update path if needed
        removeBlankLines(xmlPath);
    }
}
