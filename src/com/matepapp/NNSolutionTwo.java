package com.matepapp;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

/**
 * Created by matepapp on 2016. 10. 10..
 */

/* Input
2,3,1
1,0,-0.5
0,1,-0.5
1,1,-1
2,2,-2,0
4
0,0
0,1
1,0
1,1

Output
4
0.0
1.0
1.0
0.0
 */
public class NNSolutionTwo {
    public static void main(String[] args) {
        ArrayList<ArrayList<Double>> inputs = new ArrayList<>();
        ArrayList<ArrayList<Double>> weights = new ArrayList<>();

        InputReader reader = new InputReader();

        Integer numberOfAllNeurons = 0;

        ArrayList<Integer> architechtInput = reader.readIntegerLine();

        for (int i = 1; i < architechtInput.size(); i++)
            numberOfAllNeurons += architechtInput.get(i);

        for (int i = 0; i < numberOfAllNeurons; i++)
            weights.add(reader.readDoubleLine());

        Integer inputNumber = reader.readIntegerLine().get(0);
        for (int i = 0; i < inputNumber; i++) {
            inputs.add(reader.readDoubleLine());
        }

        ArrayList<Double> initialInput = inputs.get(0);
        NeuronNetwork neuronNetwork = new NeuronNetwork(architechtInput, weights, initialInput);
        System.out.println(inputNumber);
        neuronNetwork.printOutputTaskTwo();

        for (int i = 1; i < inputs.size(); i++) {
            ArrayList<Double> newInput = inputs.get(i);
            neuronNetwork.setInput(newInput);
            neuronNetwork.printOutputTaskTwo();
        }
    }
}
