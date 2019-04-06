package MLP;

public class SigmoidalFunction {
	
	static public double sigmoid(double x) 
	{
		return 1 / (1 + Math.pow(Math.E, - x));
	}

	static public double sigmoidDerivative(double x)
    {
        return sigmoid(x) * (1 - sigmoid(x));
    }
}
