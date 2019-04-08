package MLP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class Test {
	public static double [][] data;
	
	public static double roundDouble(double value) {
		double tmp = value;
		tmp *= 1_00_0;
		double result = Math.round(tmp);
		return result / 10000;
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
		MLP test  = new MLP(3);
		
		getData();
		Random generator = new Random(); 

		int iteration = 0;
		int j = 0;
		double currentError = 0;
		while(iteration < 500_00) {
				j = generator.nextInt(4);
				for(int i = 0 ; i < data.length ; i++) {
					currentError = test.backPropagate(data[i], data[i]);
				}
				System.out.printf("%d;%f\n", iteration, currentError);
				iteration++;
			
		}



		
	}
}
