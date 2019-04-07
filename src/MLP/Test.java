package MLP;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		MLP test  = new MLP(3);
		
//		while(test.newError > 0.05) {
//			test.backPropagate();
//		}
		for (int i = 0; i < 100000; i++) {
			test.backPropagate();
			
		}
		System.out.println(test.execute());

		
	}
}
