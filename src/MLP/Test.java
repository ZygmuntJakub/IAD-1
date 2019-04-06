package MLP;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		MLP test  = new MLP(3);
		

		System.out.println(test.execute());
		
		System.out.println(test.layers.get(0).neurons.get(2).getOutput());
	}
}
