package com.matepapp;

import java.util.ArrayList;
import java.util.Random;
import java.util.jar.Pack200;

/**
 * Created by matepapp on 2016. 10. 13..
 */
public class NeuronNetwork {
    private static ArrayList<NeuronLayer> neuronNetwork;
    private static Integer inputDimension; // bemeneti parameter
    private static Integer outputDimension; // kimeneti dimenzioszam
    private static ArrayList<Integer> architechtInput;
    private static ArrayList<Double> inputs; // bemenetkent kapott szamok eltarolasa
    private static ArrayList<ArrayList<Double>> weights;  // bemenetkent kapott sulyok eltarolasa

    public NeuronNetwork(ArrayList<Integer> archInput, ArrayList<ArrayList<Double>> weights, ArrayList<Double> inputs) {
        neuronNetwork = new ArrayList<NeuronLayer>();

        this.architechtInput = archInput;
        this.weights = weights;
        this.inputs = inputs;

        inputDimension = architechtInput.get(0);
        outputDimension = architechtInput.get(architechtInput.size() - 1);

        Integer numberOfInputs = inputDimension;    // hany dimenzios lesz az input
        Integer numberOfUsedNeurons = 0;    // eddig inicializalt neuronok szama

        // inicializalni a megfelelo szamu neuronretegeket
        for (int i = 1; i < architechtInput.size(); i++) {
            NeuronLayer layer = new NeuronLayer();
            Integer numberOfNeurons = architechtInput.get(i);
            ArrayList<Double> neuronInputs = new ArrayList<>();     // az adott retegnek megfelelo bemenetek

            // ha ez nem az elso reteg, akkor az elozo reteg kimeneteit beallitom a mostani reteg bemeneteinek
            if (i > 1) {
                layer.setPreviousLayer(neuronNetwork.get(neuronNetwork.size() - 1));
                for (int m = 0; m < numberOfInputs; m++)
                    neuronInputs.add(layer.getPreviousLayer().getNeurons().get(m).getOutput());
            }
            // egyebkent az inputokat kapja bemenetkent
            else
                neuronInputs = inputs;

            // vegigiteralni es inicializalni a megfelelo szamu neuronokat
            for (int j = 0; j < numberOfNeurons; j++) {
                Neuron neuron = new Neuron();
                ArrayList<Double> neuronWeights = weights.get(numberOfUsedNeurons + j);

                // a neuronokon belul a sulyok es a bemenetek inicializalasa
                for (int k = 0; k < numberOfInputs; k++) {
                    neuron.getInputs().add(neuronInputs.get(k));
                    neuron.getWeights().add(neuronWeights.get(k));
                    neuron.getWeightsDerivated().add(neuronWeights.get(k));
                }

                neuron.setBias(neuronWeights.get(neuronWeights.size() - 1));

                // ha az utolso neuronreteg, akkor kimeneti reteg
                if (i == architechtInput.size() - 1)
                    neuron.setOutputNeuron(true);

                layer.getNeurons().add(neuron);
            }

            neuronNetwork.add(layer);   // hozzaadom a halozathoz a reteget
            numberOfUsedNeurons += numberOfNeurons;    // az inicializalt neuronok szamahoz hozzaadom a felhasznaltakat
            numberOfInputs = layer.getNeurons().size();     // az input meretet beallitom a neuronok szamara
        }

        for (int i = 0; i < neuronNetwork.size() - 1; i++) {
            neuronNetwork.get(i).setNextLayer(neuronNetwork.get(i + 1));
        }
    }

    public NeuronNetwork(ArrayList<Integer> archInput) {
        neuronNetwork = new ArrayList<NeuronLayer>();

        this.architechtInput = archInput;
        inputDimension = architechtInput.get(0);
        outputDimension = architechtInput.get(architechtInput.size() - 1);

        Integer numberOfInputs = inputDimension;
        Integer numberOfUsedNeurons = 0;

        for (int i = 1; i < architechtInput.size(); i++) {
            NeuronLayer layer = new NeuronLayer();
            Integer numberOfNeurons = architechtInput.get(i);
            ArrayList<Double> neuronInputs = new ArrayList<>();

            if (i > 1) {
                layer.setPreviousLayer(neuronNetwork.get(neuronNetwork.size() - 1));
                for (int m = 0; m < numberOfInputs; m++)
                    neuronInputs.add(layer.getPreviousLayer().getNeurons().get(m).getOutput());
            }
            else
                for (int m = 0; m < inputDimension; m++)
                    neuronInputs.add(0.0);

            for (int j = 0; j < numberOfNeurons; j++) {
                Neuron neuron = new Neuron();

                for (int k = 0; k < numberOfInputs; k++) {
                    neuron.getInputs().add(neuronInputs.get(k));

                    Double random = getRandomNumber();
                    neuron.getWeights().add(random);
                    neuron.getWeightsDerivated().add(random);
                }

                neuron.setBias(0.0);

                if (i == architechtInput.size() - 1)
                    neuron.setOutputNeuron(true);

                layer.getNeurons().add(neuron);
            }

            neuronNetwork.add(layer);
            numberOfUsedNeurons += numberOfNeurons;
            numberOfInputs = layer.getNeurons().size();
        }

        for (int i = 0; i < neuronNetwork.size() - 1; i++) {
            neuronNetwork.get(i).setNextLayer(neuronNetwork.get(i + 1));
        }
    }

    public NeuronNetwork() {
    }

    public static double getRandomNumber() {
        Random random = new Random();
        double mean = 0.0f;
        double variance = 0.1f;

        return mean + random.nextGaussian() * variance;
    }

    public void setInput(ArrayList<Double> newInput) {
        NeuronLayer firstLayer = neuronNetwork.get(0);
        for (Neuron neuron : firstLayer.getNeurons())
            neuron.setInputs(newInput);

        for (int i = 1; i < neuronNetwork.size(); i++) {
            NeuronLayer actualLayer = neuronNetwork.get(i);

            for (Neuron neuron : actualLayer.getNeurons()) {
                ArrayList<Double> previousLayerOutputs = new ArrayList<>();

                for (Neuron previousNeuron : actualLayer.getPreviousLayer().getNeurons())
                    previousLayerOutputs.add(previousNeuron.getOutput());

                neuron.setInputs(previousLayerOutputs);
            }

        }

        for (Neuron neuron : neuronNetwork.get(neuronNetwork.size() - 1).getNeurons())
            neuron.getOutput();
    }

    public static void calculateBiasPartialDerivate() {
        NeuronLayer lastLayer = neuronNetwork.get(neuronNetwork.size() - 1);

        for (int i = 0; i < lastLayer.getNeurons().size(); i++)
            lastLayer.getNeurons().get(i).calculateBiasPartialDerivate(null, 0);

        for (int i = neuronNetwork.size() - 2; i >= 0; i--) {
            NeuronLayer actualLayer = neuronNetwork.get(i);

            for (int index = 0; index < actualLayer.getNeurons().size(); index++) {
                Neuron neuron = actualLayer.getNeurons().get(index);
                neuron.calculateBiasPartialDerivate(actualLayer.getNextLayer(), index);
            }
        }
    }

    public static void calculateWeightsPartialDerivate() {
        for (int i = neuronNetwork.size() - 1; i >= 0; i--) {
            NeuronLayer actualLayer = neuronNetwork.get(i);

            for (int index = 0; index < actualLayer.getNeurons().size(); index++) {
                Neuron neuron = actualLayer.getNeurons().get(index);
                neuron.calculateWeightsPartialDerivate();
            }
        }
    }

    public static void modifyBiasWithError(ArrayList<Double> error) {
        for (int i = neuronNetwork.size() - 1; i >= 0; i--) {
            NeuronLayer actualLayer = neuronNetwork.get(i);

            for (int index = 0; index < actualLayer.getNeurons().size(); index++) {
                Neuron neuron = actualLayer.getNeurons().get(index);

                if (error.size() == 1)
                    neuron.modifyBiasWithError(error.get(0));
                else
                    neuron.modifyBiasWithError(error.get(index));
            }
        }
    }

    public static void modifyWeightsWithError(ArrayList<Double> error) {
        for (int i = neuronNetwork.size() - 1; i >= 0; i--) {
            NeuronLayer actualLayer = neuronNetwork.get(i);

            for (int index = 0; index < actualLayer.getNeurons().size(); index++) {
                Neuron neuron = actualLayer.getNeurons().get(index);

                if (error.size() == 1)
                    neuron.modifyWeightsWithError(error.get(0));
                else
                    neuron.modifyWeightsWithError(error.get(index));
            }
        }
    }

    public static ArrayList<Double> getFinalOutPut() {
        NeuronLayer lastLayer = neuronNetwork.get(neuronNetwork.size() - 1);
        ArrayList<Double> output = new ArrayList<>();

        for (Neuron neuron : lastLayer.getNeurons())
            output.add(neuron.getOutput());

        return output;
    }

    public static void printOutputTaskOne() {
        String formatedInputString = architechtInput.toString().replace("[", "").replace("]", "").replace(" ", "").trim();
        System.out.println(formatedInputString);

        for (NeuronLayer layer : neuronNetwork) {
            ArrayList<Neuron> neurons = layer.getNeurons();

            for (Neuron neuron : neurons) {
                for (Double weight : neuron.getWeights()) {
                    System.out.print(weight + ",");
                }
                System.out.println(neuron.getBias());
            }
        }
    }

    public static void printOutputTaskTwo() {
        ArrayList<Neuron> outputNeurons = neuronNetwork.get(neuronNetwork.size() - 1).getNeurons();
        Integer size = outputNeurons.size();
        if (size == 1)
            System.out.println(outputNeurons.get(0).getOutput());
        else {
            for (int i = 0; i < size; i++) {
                if (i == size - 1)
                    System.out.print(outputNeurons.get(i).getOutput());
                else
                    System.out.print(outputNeurons.get(i).getOutput() + ",");
            }
            System.out.println();
        }
    }

    public static void printOutputTaskThree() {
        String formatedInputString = architechtInput.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();
        System.out.println(formatedInputString);

        for (NeuronLayer layer : neuronNetwork) {
            for (Neuron neuron : layer.getNeurons()) {
                for (Double weightDerivated : neuron.getWeightsDerivated())
                    System.out.print(weightDerivated + ",");

                System.out.println(neuron.getBiasDerivated());
            }
        }
    }

    public static void printOutputTaskFour() {
        String formatedInputString = architechtInput.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();
        System.out.println(formatedInputString);

        for (NeuronLayer layer : neuronNetwork) {
            for (Neuron neuron : layer.getNeurons()) {
                for (Double weight : neuron.getWeights())
                    System.out.print(weight + ",");

                System.out.println(neuron.getBias());
            }
        }
    }
}
