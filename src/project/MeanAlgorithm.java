package project;

import ti2736c.MovieList;
import ti2736c.Rating;
import ti2736c.RatingList;
import ti2736c.UserList;

/**
 * Created by Dereck on 3/5/2015.
 */
public class MeanAlgorithm extends PredictionAlgorithm {
    @Override
    public RatingList getPrediction(UserList userList, MovieList movieList, RatingList ratingList, RatingList predRatings) {
        RatingList ret = new RatingList(predRatings.size());
        double mean = ratingList.get(0).getRating();
        for (int i = 1; i < ratingList.size(); i++) {
            mean = ((double) i / ((double) i + 1.0)) * mean
                    + (1.0 / ((double) i + 1.0))
                    * ratingList.get(i).getRating();
        }

        // Predict mean everywhere
        for (int i = 0; i < predRatings.size(); i++) {
            Rating r = predRatings.get(i);
            ret.add(new Rating(r.getUser(), r.getMovie(), mean));
        }

        // Return predictions
        return ret;
    }
}
