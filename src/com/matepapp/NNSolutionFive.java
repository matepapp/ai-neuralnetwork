package com.matepapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by matepapp on 2016. 10. 16..
 */
public class NNSolutionFive {
    static ArrayList<ArrayList<Double>> learningPattern;
    static ArrayList<ArrayList<Double>> validationPattern;
    static ArrayList<ArrayList<Double>> inputs;
    static ArrayList<ArrayList<Double>> weights;
    static ArrayList<ArrayList<Double>> expectedOutputs;
    static ArrayList<ArrayList<Double>> validationExpectedOutputs;
    static ArrayList<ArrayList<Double>> validationPatternResults;
    static Integer inputDimension;
    static Integer outputDimension;
    static NeuronNetwork neuronNetwork;

    public static void main(String[] args) {
        learningPattern = new ArrayList<>();
        validationPattern = new ArrayList<>();
        inputs = new ArrayList<>();
        weights = new ArrayList<>();
        expectedOutputs = new ArrayList<>();
        validationExpectedOutputs = new ArrayList<>();

        InputReader reader = new InputReader();

        inputs = reader.readFromFile();

        Integer epoch = 2100;
        Double bravenessFactor = 0.01;
        Double learningRate = 0.8;

        ArrayList<Integer> architechtInput = new ArrayList<>(Arrays.
                asList(new Integer[] {57, 2, 1}));
        inputDimension = architechtInput.get(0);
        outputDimension = architechtInput.get(architechtInput.size() - 1);

        neuronNetwork = new NeuronNetwork(architechtInput);

        // input szamanak a beolvasasa
        Integer inputNumber = inputs.size();
        Double learningPatternNumberDouble = (inputNumber * learningRate);
        Integer learningPatternNumber = learningPatternNumberDouble.intValue();

        for (int i = 0; i < learningPatternNumber; i++) {
            ArrayList<Double> learning = inputs.get(i);
            ArrayList<Double> learningExpectedOutput = new ArrayList<>();
            Integer lastIndex = learning.size() - 1;

            for (int j = lastIndex; j > lastIndex - outputDimension; j--)
                learningExpectedOutput.add(learning.remove(j));

            Collections.reverse(learningExpectedOutput);

            expectedOutputs.add(learningExpectedOutput);
            learningPattern.add(learning);
        }

        Integer validationPatternNumber = inputNumber - learningPatternNumber;

        for (int i = 0; i < validationPatternNumber; i++) {
            ArrayList<Double> validation = inputs.get(i + learningPatternNumber);
            ArrayList<Double> validationExpectedOutput = new ArrayList<>();
            Integer lastIndex = validation.size() - 1;

            for (int j = lastIndex; j > lastIndex - outputDimension; j--)
                validationExpectedOutput.add(validation.remove(j));

            Collections.reverse(validationExpectedOutput);

            validationExpectedOutputs.add(validationExpectedOutput);
            validationPattern.add(validation);
        }

        for (int j = 0; j < epoch; j++) {
            for (int i = 0; i < learningPattern.size(); i++) {
                ArrayList<Double> input = learningPattern.get(i);
                neuronNetwork.setInput(input);

                ArrayList<Double> error = new ArrayList<>();
                Double errorNumber = 0.0;
                ArrayList<Double> expectedOutput = expectedOutputs.get(i);
                ArrayList<Double> finalOutPut = neuronNetwork.getFinalOutPut();

                for (int k = 0; k < expectedOutput.size(); k++) {
                    errorNumber = expectedOutput.get(k) - finalOutPut.get(k);
                    errorNumber = errorNumber * bravenessFactor * 2;
                    error.add(errorNumber);
                }

                neuronNetwork.calculateBiasPartialDerivate();
                neuronNetwork.calculateWeightsPartialDerivate();
                neuronNetwork.modifyBiasWithError(error);
                neuronNetwork.modifyWeightsWithError(error);
            }

            validationPatternResults = new ArrayList<>();

            for (int i = 0; i < validationPatternNumber; i++) {
                neuronNetwork.setInput(validationPattern.get(i));
                validationPatternResults.add(neuronNetwork.getFinalOutPut());
            }
        }

        neuronNetwork.printOutputTaskFour();
    }

    public static Double calculateAverageSquareError() {
        ArrayList<Double> errorArray = new ArrayList<>();
        Double difference = 0.0;
        Double averageSquareError = 0.0;

        for (int i = 0; i < validationPatternResults.size(); i++) {
            ArrayList<Double> averageSquareErrorArray = new ArrayList<>();

            ArrayList<Double> validationExpectedOutput = validationExpectedOutputs.get(i);
            ArrayList<Double> validationPatternResult = validationPatternResults.get(i);

            for (int j = 0; j < validationPatternResult.size(); j++) {
                difference = validationExpectedOutput.get(j) - validationPatternResult.get(j);
                difference = difference * difference;
                averageSquareErrorArray.add(difference);
            }

            Double sum = 0.0;
            for (Double error : averageSquareErrorArray)
                sum += error;
            averageSquareError = sum / averageSquareErrorArray.size();
            errorArray.add(averageSquareError);
        }

        Double result = 0.0;
        for (Double error : errorArray)
            result += error;

        averageSquareError = result / validationPatternResults.size();
        return averageSquareError;
    }
}
