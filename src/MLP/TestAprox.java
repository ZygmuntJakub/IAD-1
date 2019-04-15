package MLP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class TestAprox {
	public static double [][] data1;
	public static int [] randIterator;
	static Random generator;
	
	public static int getRandIterator(int count) {
		generator = new Random(); 
		int j = generator.nextInt(count);
		boolean get = true;
		while(get) {
			if(randIterator[j] == 1) {
				randIterator[j] = 0;
				get = false;
			}else {
				j = generator.nextInt(count);
			}
		}
		return j;
		
	}
	public static void resetRandIterator() {
		for(int i = 0 ; i < randIterator.length ; i++) {
			randIterator[i] = 1;
		}
	}
	
	public static double roundDouble(double value) {
		double tmp = value;
		tmp *= 1_00_0;
		double result = Math.round(tmp);
		return result / 10000;
	}
	
	public static void getData1() throws IOException {
		data1 = new double[81][2];
		InputStream is = new FileInputStream("approximation_train_1.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		int i = 0;
		while (line != null) {
			data1[i][0] = Double.parseDouble(line.split(" ")[0]);
			data1[i][1] = Double.parseDouble(line.split(" ")[1]);
			i++;
			line = buf.readLine();
		}
		buf.close();
	}

	public static void main(String[] args) throws IOException {
		MLP_APROX test  = new MLP_APROX(17, 1);
		
		getData1();

		int iteration = 0;
		int iterator = 0;
		double currentError = 0;
		randIterator = new int[81];
		resetRandIterator();
		while(iteration < 10_001) {
				for(int i = 0 ; i < data1.length ; i++) {
					iterator = getRandIterator(81);
					currentError = test.backPropagate(data1[iterator][1], data1[iterator][0]);
				}
				//System.out.printf("%f\n", currentError);
				iteration++;
				resetRandIterator();
			
		}
		double x_value = -4.0;
		while(x_value <= 4.0) {
			System.out.println(x_value + ";" + test.execute(x_value));
			x_value += 0.001;
		}



		
	}
}
