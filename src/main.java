
public class main {

	public static void perceptron() {
        Dataset dataset = new Dataset("data/guassian.txt", true);
        Perceptron perceptron = new Perceptron(1.0d);
        for (FeatureVector featureVector : dataset) {
            perceptron.train(featureVector);
        }
        PerceptronPlotter perceptronPlotter = new PerceptronPlotter("first", "second");
        perceptronPlotter.plotData(dataset, perceptron);

		// add code here
	}

	public static void perceptronDigits() {
		// add code here
	}

	public static void nearestNeighbour() {
		// add code here
	}
	
	public static void nearestNeighbourDigits() {
		// add code here
	}

	public static void main(String[] args) {
		//perceptron();
		//perceptronDigits();
		//nearestNeighbour();
		//nearestNeighbourDigits();
	}

}
