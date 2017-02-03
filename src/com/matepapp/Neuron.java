package com.matepapp;

import java.util.ArrayList;

/**
 * Created by matepapp on 2016. 10. 10..
 */
public class Neuron {
    private Double bias;
    private Double biasDerivated;
    private ArrayList<Double> weights;
    private ArrayList<Double> weightsDerivated;
    private ArrayList<Double> inputs;
    private boolean isOutputNeuron;
    private Double output;

    // MARK: Constructors
    public Neuron(Double bias, Double biasDerivated, ArrayList<Double> weights, ArrayList<Double> weightsDerivated, ArrayList<Double> inputs, boolean isOutputNeuron, Double output) {
        this.bias = bias;
        this.biasDerivated = biasDerivated;
        this.weights = weights;
        this.weightsDerivated = weightsDerivated;
        this.inputs = inputs;
        this.isOutputNeuron = isOutputNeuron;
        this.output = output;
    }

    public Neuron() {
        this.bias = 0.0;
        this.biasDerivated = 0.0;
        this.weights = new ArrayList<>();
        this.weightsDerivated = new ArrayList<>();
        this.inputs = new ArrayList<>();
        this.isOutputNeuron = false;
        this.output = 0.0;
    }

    // MARK: Getters and Setters

    public Double getBiasDerivated() {
        return biasDerivated;
    }

    public void setBiasDerivated(Double biasDerivated) {
        this.biasDerivated = biasDerivated;
    }

    public ArrayList<Double> getWeightsDerivated() {
        return weightsDerivated;
    }

    public void setWeightsDerivated(ArrayList<Double> weightsDerivated) {
        this.weightsDerivated = weightsDerivated;
    }

    public Double getBias() {
        return bias;
    }

    public void setBias(Double bias) {
        this.bias = bias;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    public ArrayList<Double> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<Double> inputs) {
        this.inputs = inputs;
    }

    public void addToInputs(Double input) {
        inputs.add(input);
    }

    public boolean isOutputNeuron() {
        return isOutputNeuron;
    }

    public void setOutputNeuron(boolean outputNeuron) {
        isOutputNeuron = outputNeuron;
    }

    public Double getOutput() {
        Double calculatedOutput = bias;

        for (int i = 0; i < inputs.size(); i++) {
            calculatedOutput += inputs.get(i) * weights.get(i);
        }

        if (this.isOutputNeuron)
            output = linearActivationFunction(calculatedOutput);
        else
            output = rectifierActivationFunction(calculatedOutput);

        return output;
    }

    // MARK: Functions
    public Double linearActivationFunction(Double x) {
        return x;
    }

    public Double rectifierActivationFunction(Double x) {
        if (x > 0)
            return x;
        else
            return 0.0;
    }

    public Double derivatedLinearActivationFunction(Double x) {
        return 1.0;
    }

    public Double derivatedRectifierActivationFunction(Double x) {
        if (x > 0)
            return 1.0;
        else
            return 0.0;
    }

    public void calculateBiasPartialDerivate(NeuronLayer nextLayer, int index) {
        if (nextLayer == null)
            biasDerivated = derivatedLinearActivationFunction(1.0);
        else {
            Double sum = 0.0;

            for (int j = 0; j < nextLayer.getNeurons().size(); j++) {
                Neuron neuron = nextLayer.getNeurons().get(j);
                sum += neuron.getBiasDerivated() * neuron.getWeights().get(index);
            }

            biasDerivated = sum * derivatedRectifierActivationFunction(output);
        }
    }

    public void calculateWeightsPartialDerivate() {
        for (int j = 0; j < weights.size(); j++)
            weightsDerivated.set(j, biasDerivated * inputs.get(j));
    }

    public void modifyBiasWithError(Double error) {
        bias = bias + biasDerivated * error;
    }

    public void modifyWeightsWithError(Double error) {
        for (int i = 0; i < weights.size(); i++) {
            Double newWeight = weights.get(i) + weightsDerivated.get(i) * error;
            weights.set(i, newWeight);
        }
    }
}