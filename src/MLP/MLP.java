package MLP;

import java.io.IOException;

public class MLP {
	public Layer[] layers;
	public boolean orBias;
	public double newError = 1.0;

	public MLP(int numberOfHiddenNeurons, int numberOfIO) throws IOException {
		int p = numberOfHiddenNeurons;
		orBias = true;
		layers = new Layer[3];
		Parameters.learningRate = 0.1;
		Parameters.momentum = 0.0;
		layers[0] = new Layer(numberOfIO, 0);
		layers[1] = new Layer(p, numberOfIO);
		layers[2] = new Layer(numberOfIO, p);
	}



	public double [] execute(double [] inputs) {
		double [] result = new double[inputs.length];
		double newOutput = 0.0;

			// Wprowadzamy wartości wejściowe
			for (int i = 0; i < layers[0].size; i++) {
				layers[0].neurons[i].output = inputs[i];
			}
			// obliczamy wartości wyjściowe dla każdego neuronu z warstwy ukrytej
			for (int k = 1; k < layers.length; k++) {
				for (int i = 0; i < layers[k].size; i++) {
					newOutput = 0.0;
					for (int j = 0; j < layers[k - 1].size; j++) {
						newOutput += layers[k].neurons[i].weights[j] * layers[k - 1].neurons[j].output;
					}
					if (orBias)
						newOutput += layers[k].neurons[i].bias;
					layers[k].neurons[i].a = newOutput;
					layers[k].neurons[i].output = SigmoidalFunction.sigmoid(newOutput);

				}
			}
			
			// wpisujemy do wektora wyliczone wartości wyjściowe
			for (int i = 0; i < layers[2].size; i++) {
				result[i] =  layers[2].neurons[i].output;
			}
		return result;
	}

	public double backPropagate(double [] outputs, double []inputs) {
		double[] newOutputs = execute(inputs);
		double error = 0.0;
		newError = 0.0;

			// wyznaczamy błąd na wyjściu
			for (int i = 0; i < layers[2].size; i++) {
				error = newOutputs[i] - outputs[i];
				layers[2].neurons[i].delta = error * SigmoidalFunction.sigmoidDerivative(outputs[i]);
			}

			for (int k = layers.length - 2; k >= 0; k--) {
				for (int i = 0; i < layers[k].size; i++) {
					newError = 0.0;
					for (int j = 0; j < layers[k + 1].size; j++) {
						newError += layers[k + 1].neurons[j].delta * layers[k + 1].neurons[j].weights[i];
					}
					layers[k].neurons[i].delta = newError
							* SigmoidalFunction.sigmoidDerivative(layers[k].neurons[i].a);
				}
				// aktualizacja wag
				for (int i = 0; i < layers[k + 1].size; i++) {
					for (int j = 0; j < layers[k].size; j++) {

						layers[k + 1].neurons[i].weights[j] += (-1 * Parameters.learningRate
								* layers[k + 1].neurons[i].delta * layers[k].neurons[j].output)
								+ (Parameters.momentum * layers[k + 1].neurons[i].oldDelta[j]);

						layers[k + 1].neurons[i].oldDelta[j] = (-1 * Parameters.learningRate
								* layers[k + 1].neurons[i].delta * layers[k].neurons[j].output)
								+ (Parameters.momentum * layers[k + 1].neurons[i].oldDelta[j]);

					}
					if (orBias)
						layers[k + 1].neurons[i].bias += -1 * Parameters.learningRate * layers[k + 1].neurons[i].delta;
				}
			}

			newError = 0.0;
			for (int i = 0; i < outputs.length; i++) {
				newError += (Math.pow(newOutputs[i] - outputs[i], 2))/2;
			}

			return newError;


	}
}
