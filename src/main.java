
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

        Perceptron perceptron = new Perceptron(1d);
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
        nearestNeighbour.readData("data/banana.txt");
        NearestNeighbourPlotter nearestNeighbourPlotter = new NearestNeighbourPlotter(k);
        nearestNeighbourPlotter.plotData(nearestNeighbour);

	}
	
	public static void nearestNeighbourDigits() {

        int k = 3;
        NearestNeighbour nearestNeighbour = new NearestNeighbour();
        //NearestNeighbourPlotter nearestNeighbourPlotter = new NearestNeighbourPlotter(k);
        nearestNeighbour.readData("data/train_digits.txt");
        int misclassified = 0;
        for (FeatureVector fv : nearestNeighbour.dataset) {
            int predicted = nearestNeighbour.predict(fv, k);
            if (predicted != fv.getLabel()) {
                misclassified++;
                System.out.println("Misclassified " + misclassified);
            }
        }

        System.out.println("Miss rate: " + ((double) misclassified / (double) nearestNeighbour.dataset.size()));

        //nearestNeighbourPlotter.plotData(nearestNeighbour);
	}

	public static void main(String[] args) {
		//perceptron();
		perceptronDigits();
		//nearestNeighbour();
		//nearestNeighbourDigits();
	}

}
