package MLP;

public class Layer {
	public Neuron []neurons;
	public int size;
	
	public Layer(int size, int inputs) {
		neurons = new Neuron[size];
		this.size = size;
		for (int i = 0; i < size; i++) {
			neurons[i] = new Neuron(inputs);
		}
	}
}
