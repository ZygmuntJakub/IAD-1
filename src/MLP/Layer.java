package MLP;

import java.util.Vector;

public class Layer {
	public Vector<Neuron> neurons;
	public int size;
	
	public Layer(int size, int inputs) {
		neurons = new Vector<>();
		for (int i = 0; i < size; i++) {
			neurons.add(new Neuron(inputs));
		}
	}
}
