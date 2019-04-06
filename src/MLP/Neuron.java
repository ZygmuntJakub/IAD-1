package MLP;

import MLP.Layer;
import MLP.Parameters;
import MLP.SigmoidalFunction;

public class Neuron {
	public double weights[];
	public double inputs[];
	public double delta;
	private double output;
	public double gradient;
	
	public Neuron(int numberOfInputs) {
		weights = new double[numberOfInputs + 1];
		inputs = new double[numberOfInputs + 1];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = randomWeight();
		}
		setInput(0, 1);		//gdy jest bias to wejœcie na biasie = 1, gdy nie ma biasu = 0
	}
	
	private double randomWeight() {
		return Math.random() * 2 - 1;
	}
	
	private double sumBlock() {		//blok sumuj¹cy (oblicza sumê iloczynów wag i wejœæ)
		double sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i] * inputs[i];
		}
		return sum;
	}
	
	public void calculateOutput() {		//oblicza wartosæ na wyjœciu neuronu
		output = SigmoidalFunction.sigmoid(sumBlock());
	}
	
	public void setInput(int number, double value) {
		inputs[number] = value;
	}
	
	public double getOutput() {
		return output;
	}
	
	public int inputSize() {
		return inputs.length;
	}
	
	
	
	
	
	
	
	public double sumPrevLayerInput(Layer prevLayer) {	//oblicza wejœcie dla danego Neuronu
		double result = 0.0;
		for(int i = 0 ; i < prevLayer.neurons.size() ; i++) {
			result += prevLayer.neurons.get(i).output * prevLayer.neurons.get(i).weights[i];
		}
		return SigmoidalFunction.sigmoid(result);
	}
	
	public void calculateOutputLayerGradients(double expectedValue) {
		double delta = expectedValue - output;
		gradient = delta * SigmoidalFunction.sigmoidDerivative(output);
	}
	
	public void calculateHiddenGradients(Layer nextLayer) {
		double calculatedNextLayer = 0.0;
		for(int i = 0 ; i < nextLayer.neurons.size() - 1 ; i++) {
			calculatedNextLayer += weights[i] * nextLayer.neurons.get(i).gradient;
		}
		gradient = calculatedNextLayer * SigmoidalFunction.sigmoidDerivative(output);
	}
	
	public void updateWeights(Layer prevLayer) {
		for(int i = 0 ; i < prevLayer.neurons.size() ; i++) {
			prevLayer.neurons.get(i).delta = Parameters.learningRate * prevLayer.neurons.get(i).output * gradient + Parameters.momentum * prevLayer.neurons.get(i).delta;
			prevLayer.neurons.get(i).weights[i] += prevLayer.neurons.get(i).delta;
		}
	}
}
