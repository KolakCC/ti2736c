package project;

public class WeightedPrediction {
    double weight;
    PredictionAlgorithm alg;

    public WeightedPrediction(double weight, PredictionAlgorithm alg) {
        this.weight = weight;
        this.alg = alg;
    }

    public double getWeight() {
        return weight;
    }

    public PredictionAlgorithm getAlg() {
        return alg;
    }
}