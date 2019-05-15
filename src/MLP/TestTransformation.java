package MLP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class TestTransformation {
	public static double[][] data;
	public static int[] randIterator;
	static Random generator;

	public static int getRandIterator() {
		generator = new Random();
		int j = generator.nextInt(4);
		boolean get = true;
		while (get) {
			if (randIterator[j] == 1) {
				randIterator[j] = 0;
				get = false;
			} else {
				j = generator.nextInt(4);
			}
		}
		return j;

	}

	public static void resetRandIterator() {
		for (int i = 0; i < randIterator.length; i++) {
			randIterator[i] = 1;
		}
	}

	public static void getData() throws IOException {
		data = new double[4][4];
		InputStream is = new FileInputStream("transformation.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		int i = 0;
		while (line != null) {
			data[i][0] = Integer.parseInt(line.split(" ")[0]);
			data[i][1] = Integer.parseInt(line.split(" ")[1]);
			data[i][2] = Integer.parseInt(line.split(" ")[2]);
			data[i][3] = Integer.parseInt(line.split(" ")[3]);
			i++;
			line = buf.readLine();
		}
		buf.close();
	}

	public static void main(String[] args) throws IOException {
		MLP test = new MLP(2, 4);

		getData();

		int iteration = 0;
		int iterator = 0;
		double currentError = 0;
		randIterator = new int[4];
		resetRandIterator();
		do {
			currentError = 0.0;
			for (int i = 0; i < data[0].length; i++) {
				iterator = getRandIterator();
				currentError += test.backPropagate(data[iterator], data[iterator]);
			}
//				System.out.printf("%d;%f\n", iteration, currentError/4.0);
			resetRandIterator();
			iteration++;

		} while (iteration < 100_000);


		test.execute(data[0]);
		System.out.println("1000");
		System.out.println(test.layers[1].neurons[0].output);
		System.out.println(test.layers[1].neurons[1].output);
		
		test.execute(data[1]);
		System.out.println("0100");
		System.out.println(test.layers[1].neurons[0].output);
		System.out.println(test.layers[1].neurons[1].output);
		
		test.execute(data[2]);
		System.out.println("0010");
		System.out.println(test.layers[1].neurons[0].output);
		System.out.println(test.layers[1].neurons[1].output);

		test.execute(data[3]);
		System.out.println("0001");
		System.out.println(test.layers[1].neurons[0].output);
		System.out.println(test.layers[1].neurons[1].output);
	}
}