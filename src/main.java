
public class main {

	public static void perceptron() {
        Dataset dataset = new Dataset("data/gaussian.txt", true);
        Perceptron perceptron = new Perceptron(1.0d);
        PerceptronPlotter perceptronPlotter = new PerceptronPlotter("first", "second");

        for (FeatureVector featureVector : dataset) {
            perceptron.train(featureVector);
        }
        perceptronPlotter.plotData(dataset, perceptron);


		// add code here
	}

	public static void perceptronDigits() {
		DigitFrame digitFrame = new DigitFrame("Perceptron");
        Dataset dataset = new Dataset("data/train_digits.txt", true);

        Perceptron perceptron = new Perceptron(0.5d);
        perceptron.updateWeights(dataset);
        perceptron.updateWeights(dataset);
        //perceptron.updateWeights(dataset);

        Dataset testDataset = new Dataset("data/test_digits.txt", true);
        int misclassified = 0;
        for (FeatureVector fv : testDataset) {
            int predicted = perceptron.predict(fv);
            if (predicted != fv.getLabel()) {
                new DigitFrame("Misclassified " + misclassified, fv, 8, 8);
                misclassified++;
            }
        }

        System.out.println("Miss rate: " + ((double) misclassified / (double) testDataset.size()));

        digitFrame.showImage(perceptron.getWeights(), 8, 8);
	}

	public static void nearestNeighbour() {
		// add code here
        int k = 3;
        NearestNeighbour nearestNeighbour = new NearestNeighbour();
        NearestNeighbourPlotter nearestNeighbourPlotter = new NearestNeighbourPlotter(k);
        nearestNeighbour.readData("data/banana.txt");
        nearestNeighbourPlotter.plotData(nearestNeighbour);

	}
	
	public static void nearestNeighbourDigits() {
        DigitFrame digitFrame = new DigitFrame("Perceptron");
        Dataset dataset = new Dataset("data/train_digits.txt", true);

        Perceptron perceptron = new Perceptron(0.5d);
        perceptron.updateWeights(dataset);
        perceptron.updateWeights(dataset);
        //perceptron.updateWeights(dataset);

        Dataset testDataset = new Dataset("data/test_digits.txt", true);
        int misclassified = 0;
        for (FeatureVector fv : testDataset) {
            int predicted = perceptron.predict(fv);
            if (predicted != fv.getLabel()) {
                new DigitFrame("Misclassified " + misclassified, fv, 8, 8);
                misclassified++;
            }
        }

        System.out.println("Miss rate: " + ((double) misclassified / (double) testDataset.size()));

        digitFrame.showImage(perceptron.getWeights(), 8, 8);
	}

	public static void main(String[] args) {
		//perceptron();
		//perceptronDigits();
		nearestNeighbour();
		//nearestNeighbourDigits();
	}

}
