package MLP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class MLP {
	public Layer[] layers;
	public Vector<Vector<Integer>> data;
	public boolean orBias;
	public double newError = 1.0;

	public MLP(int numberOfHiddenNeurons) throws IOException {
		orBias = true;
		layers = new Layer[3];
		Parameters.learningRate = 0.1;
		Parameters.momentum = 0.6;
		getData();
		layers[0] = new Layer(4, 0);
		layers[1] = new Layer(3, 4);
		layers[2] = new Layer(4, 3);
	}

	public void getData() throws IOException {
		data = new Vector<>();
		InputStream is = new FileInputStream("transformation.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		while (line != null) {
			Vector<Integer> values = new Vector<>();
			values.add(Integer.parseInt(line.split(" ")[0]));
			values.add(Integer.parseInt(line.split(" ")[1]));
			values.add(Integer.parseInt(line.split(" ")[2]));
			values.add(Integer.parseInt(line.split(" ")[3]));
			data.add(values);
			line = buf.readLine();
		}
		buf.close();
	}

	public Vector<Vector<Double>> execute() {
		Vector<Vector<Double>> result = new Vector<>();
		double newOutput = 0.0;
		for (int d = 0; d < data.size(); d++) {

			Vector<Double> res = new Vector<>();

			// Wprowadzamy wartości wejściowe
			for (int i = 0; i < layers[0].size; i++) {
				layers[0].neurons[i].output = data.get(d).get(i);
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
					layers[k].neurons[i].output = SigmoidalFunction.sigmoid(newOutput);
				}
			}
			// wpisujemy do wektora wyliczone wartości wyjściowe
			for (int i = 0; i < layers[2].size; i++) {
				res.add(layers[2].neurons[i].output);
			}
			result.add(res);
		}
		return result;
	}

	public void backPropagate() {
		Vector<Vector<Double>> outputs = execute();
		double error = 0.0;

		for (int x = 0; x < outputs.size(); x++) {

			// wyznaczamy błąd na wyjściu
			for (int i = 0; i < layers[2].size; i++) {
				error = outputs.get(x).get(i) - data.get(x).get(i);
				layers[2].neurons[i].delta = error * SigmoidalFunction.sigmoidDerivative(outputs.get(x).get(i));
			}

			for (int k = layers.length - 2; k >= 0; k--) {
				for (int i = 0; i < layers[k].size; i++) {
					newError = 0.0;
					for (int j = 0; j < layers[k + 1].size; j++) {
						newError += layers[k + 1].neurons[j].delta * layers[k + 1].neurons[j].weights[i];
					}
					layers[k].neurons[i].delta = newError
							* SigmoidalFunction.sigmoidDerivative(layers[k].neurons[i].output);
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
						layers[k + 1].neurons[i].bias = Parameters.learningRate * layers[k + 1].neurons[i].delta;
				}
			}

			newError = 0.0;
			for (int i = 0; i < outputs.get(0).size(); i++) {
				newError += Math.pow(outputs.get(x).get(i) - data.get(x).get(i), 2);
			}
			newError = newError / (2.0 * outputs.get(0).size());

			System.out.println(newError);

		}

	}
}
