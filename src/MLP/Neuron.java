package MLP;

public class Neuron {
	public double		output;
	public double[]		weights;
	public double		bias;
	public double		delta;
	public double[] 	oldDelta;
	
	public Neuron(int numberOfInputs) {
		weights = new double[numberOfInputs];
		oldDelta = new double[numberOfInputs];
		output = randomWeight();
		delta = randomWeight();
		for (int i = 0; i < weights.length; i++) {
			weights[i] = randomWeight();
		}
		bias = randomWeight();
		
		for(int i = 0 ; i < oldDelta.length ; i++) {
			oldDelta[i] = 0.0;
		}
	}
	
	private double randomWeight() {
		return Math.random() * 2 - 1;
	}
}
