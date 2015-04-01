package project;

import java.util.Random;

import ti2736c.Rating;
import ti2736c.RatingList;

/**
 * Created by Dereck on 01/04/2015.
 */
public class RandomRatingPredictionAlgorithm extends PredictionAlgorithm {
    public RandomRatingPredictionAlgorithm() {
        super("RandomRatingPredictionAlgorithm");
    }

    @Override
    public RatingList getPrediction(PredictionData data, RatingList ratingsToPredict) {
        Random random = new Random();
        for (Rating r: ratingsToPredict) {
            double rng = random.nextGaussian();
            double mean = 3.5813148902975604;

            double result;
            if (rng > 0) result = rng * (5 - mean);
            else result = rng * (mean - 1);

            r.setRating(result + mean);
        }
        return ratingsToPredict;
    }
}
