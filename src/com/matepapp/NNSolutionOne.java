package com.matepapp;

import java.util.*;

public class NNSolutionOne {
    public static void main(String[] args) {
        InputReader reader = new InputReader();

        ArrayList<Integer> architechtInputs = reader.readIntegerLine();

        NeuronNetwork neuronNetwork = new NeuronNetwork(architechtInputs);
        neuronNetwork.printOutputTaskOne();
    }
}
