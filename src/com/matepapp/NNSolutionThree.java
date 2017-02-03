package com.matepapp;

import java.util.ArrayList;

/**
 * Created by matepapp on 2016. 10. 11..
 */

/*
Input
2,3,1
1,0,-0.5
0,1,-0.5
1,1,-1
2,2,-2,0
1
0.75,0.75

Output
2,3,1
1.5,1.5,2.0
1.5,1.5,2.0
-1.5,-1.5,-2.0
0.25,0.25,0.5,1.0
 */
public class NNSolutionThree {
    public static void main(String[] args) {
        ArrayList<Double> inputs = new ArrayList<>();
        ArrayList<ArrayList<Double>> weights = new ArrayList<>();

        InputReader reader = new InputReader();

        Integer numberOfAllNeurons = 0;

        ArrayList<Integer> architechtInput = reader.readIntegerLine();

        // az osszes neuronszam megkeresese, mert annyi beolvasott sornyi suly van
        for (int i = 1; i < architechtInput.size(); i++) {
            numberOfAllNeurons += architechtInput.get(i);
        }

        // sulyok beolvasasa
        for (int i = 0; i < numberOfAllNeurons; i++) {
            weights.add(reader.readDoubleLine());
        }

        // input szamanak a beolvasasa
        Integer inputNumber = reader.readIntegerLine().get(0);
        inputs = reader.readDoubleLine();

        NeuronNetwork neuronNetwork = new NeuronNetwork(architechtInput, weights, inputs);
        neuronNetwork.calculateBiasPartialDerivate();
        neuronNetwork.calculateWeightsPartialDerivate();
        neuronNetwork.printOutputTaskThree();
    }
}
