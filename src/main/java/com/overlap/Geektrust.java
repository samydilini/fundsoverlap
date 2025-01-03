package com.overlap;

import com.overlap.exception.FundsOverlapException;
import com.overlap.service.FundsOverlapService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Geektrust class serves as the entry point for the application.
 * It reads input from a file and processes it using the FundsOverlapService.
 */
public class Geektrust {
    private static FundsOverlapService fundsOverlapService;

    /**
     * Entry point to the Geektrust application
     * args user arguments, expects the first argument to be the file path
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the file path as the first argument.");
            return;
        }
        setFundsOverlapService(new FundsOverlapService());
        String filePath = args[0];

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            processFile(reader);
        } catch (FileNotFoundException e) {
            System.out.println("The file you specified was not found. Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception at reading the file. Error: " + e.getMessage());
        }
    }

    /**
     * Processes the file opened by the reader and calls the fundsOverlapService to start the application processing.
     *
     * @param reader BufferedReader instance with the file to be processed
     */
    static void processFile(BufferedReader reader) {
        try {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            fundsOverlapService.process(lines);
        } catch (IOException e) {
            System.out.println("Exception at reading the file. Error: " + e.getMessage());
        } catch (FundsOverlapException e) {
            System.out.println("Exception while handling funds overlap calculations: " + e.getMessage());
        }
    }

    /**
     * Setter for the fundsOverlapService service.
     *
     * @param fundsOverlapService instance of FundsOverlapService to be set
     */
    public static void setFundsOverlapService(FundsOverlapService fundsOverlapService) {
        Geektrust.fundsOverlapService = fundsOverlapService;
    }
}
