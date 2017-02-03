package com.matepapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by matepapp on 2016. 10. 10..
 */

public class InputReader {
    public static Scanner scanner;
    public static Scanner fileScanner;

    public InputReader() {
        try {
            fileScanner = new Scanner(new File("./src/spambase_train.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(System.in);
    }

    public static ArrayList<Integer> readIntegerLine() {

        ArrayList<Integer> inputs = new ArrayList<>();

        String stringInput = (scanner.hasNextLine() ? scanner.nextLine() : "");
        List<String> stringInputArray = Arrays.asList(stringInput.split(","));

        for (String string : stringInputArray)
            inputs.add(Integer.parseInt(string));

        return inputs;
    }

    public static ArrayList<Double> readDoubleLine() {
        ArrayList<Double> inputs = new ArrayList<>();

        String stringInput = (scanner.hasNextLine() ? scanner.nextLine() : "");
        List<String> stringInputArray = Arrays.asList(stringInput.split(","));

        for (String string : stringInputArray)
            inputs.add(Double.parseDouble(string));

        return inputs;
    }

    public static ArrayList<ArrayList<Double>> readFromFile() {
        ArrayList<ArrayList<Double>> output = new ArrayList<ArrayList<Double>>();

        while(fileScanner.hasNextLine()) {
            ArrayList<Double> doubleLine = new ArrayList<>();
            String line = fileScanner.nextLine();

            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");

            while(lineScanner.hasNextDouble())
                doubleLine.add(lineScanner.nextDouble());

            output.add(doubleLine);
            lineScanner.close();
        }
        fileScanner.close();

        return output;
    }
}