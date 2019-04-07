package MLP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Vector;

public class Test {
	public static Vector<Vector<Integer>> data;
	
	public static void getData() throws IOException {
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

	public static void main(String[] args) throws IOException {
		MLP test  = new MLP(3);
		double [] getData1 = {1, 0, 0, 0 };
		double [] getData2 = {0, 1, 0, 0 };
		double [] getData3 = {0, 0, 1, 0 };
		double [] getData4 = {0, 0, 0, 1 };
		
		double backPropagateError = 10.0;
		while(backPropagateError > 0.000001) {
			backPropagateError = 0;
			test.backPropagate(getData1, getData1);
			backPropagateError += test.newError;
			test.backPropagate(getData2, getData2);
			backPropagateError += test.newError;
			test.backPropagate(getData3, getData3);
			backPropagateError += test.newError;
			test.backPropagate(getData4, getData4);
			backPropagateError += test.newError;
			backPropagateError = backPropagateError / (2 * getData1.length);
		}
		System.out.println(Arrays.toString(test.execute(getData1)));
		System.out.println(Arrays.toString(test.execute(getData2)));
		System.out.println(Arrays.toString(test.execute(getData3)));
		System.out.println(Arrays.toString(test.execute(getData4)));
//		for (int i = 0; i < 250000; i++) {
//			test.backPropagate();
//			
//		}
//		System.out.println(test.execute());

		
	}
}
