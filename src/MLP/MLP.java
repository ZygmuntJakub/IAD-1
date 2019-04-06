package MLP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;


public class MLP {
	public Vector<Layer> layers;
	public Vector<Vector<Integer>> data;
	public Vector<Vector<Integer>> expected;
	Parameters parameters;
	
	public MLP(int numberOfHiddenNeurons) throws IOException {
		layers = new Vector<>();
		parameters = new Parameters();
		getData();
		expected = data;
		layers.add(new Layer(numberOfHiddenNeurons, 4));
		layers.add(new Layer(4, numberOfHiddenNeurons));
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
		for (int i = 0; i < data.size(); i++) {
			
			for (int j = 0; j < layers.get(0).neurons.size(); j++) {
				
				for (int k = 0; k < layers.get(0).neurons.get(j).inputSize() - 1; k++) {  //wpisuje wartosci wejsciowe do neuronów z warstwy ukrytej
					layers.get(0).neurons.get(j).setInput(k+1, data.get(0).get(k));
				}
				layers.get(0).neurons.get(j).calculateOutput();
				
				Vector<Double> res = new Vector<>();
				for (int z = 0; z < layers.get(1).neurons.size(); z++) {  //wpisuje wartoœci wyjœciowe z warstwy ukrytej do neuronów z wartwy wyjœciowej i tworzy wektor wyników
					layers.get(1).neurons.get(z).setInput(1, layers.get(0).neurons.get(0).getOutput());
					layers.get(1).neurons.get(z).calculateOutput();
					res.add(layers.get(1).neurons.get(z).getOutput());
				}
				result.add(res);
			}
		}
		return result;
	}
}
