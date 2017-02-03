package com.matepapp;

import java.util.ArrayList;

/**
 * Created by matepapp on 2016. 10. 10..
 */
public class NeuronLayer {
    private ArrayList<Neuron> neurons;
    private NeuronLayer previousLayer;
    private NeuronLayer nextLayer;

    public NeuronLayer(ArrayList<Neuron> neurons, NeuronLayer previousLayer, NeuronLayer nextLayer) {
        this.neurons = neurons;
        this.previousLayer = previousLayer;
        this.nextLayer = nextLayer;
    }

    public NeuronLayer() {
        this.neurons = new ArrayList<Neuron>();
        this.previousLayer = null;
        this.nextLayer = null;
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(ArrayList<Neuron> neurons) {
        this.neurons = neurons;
    }

    public NeuronLayer getPreviousLayer() {
        return previousLayer;
    }

    public void setPreviousLayer(NeuronLayer previousLayer) {
        this.previousLayer = previousLayer;
    }

    public NeuronLayer getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(NeuronLayer nextLayer) {
        this.nextLayer = nextLayer;
    }
}
