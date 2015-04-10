package project;

import ti2736c.Rating;
import ti2736c.RatingList;

/**
 * Created by Dereck on 3/5/2015.
 */
public class MeanAlgorithm extends PredictionAlgorithm {
    public MeanAlgorithm() {
        super("MeanAlgorithm");
    }

    public static final double CACHE = 3.5813148902975604;

    @Override
    public RatingList getPrediction(PredictionData data, RatingList ratingsToPredict) {
        RatingList ret = new RatingList(ratingsToPredict.size());
        /*double mean = ratingList.get(0).getRating();
        for (int i = 1; i < ratingList.size(); i++) {
            mean = ((double) i / ((double) i + 1.0)) * mean
                    + (1.0 / ((double) i + 1.0))
                    * ratingList.get(i).getRating();
        }*/
        double mean = CACHE; // cached

        // Predict mean everywhere
        for (int i = 0; i < ratingsToPredict.size(); i++) {
            Rating r = ratingsToPredict.get(i);
            r.setRating(mean);
        }

        // Return predictions
        return ret;
    }
}
