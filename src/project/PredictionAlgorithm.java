package project;

import ti2736c.MovieList;
import ti2736c.RatingList;
import ti2736c.UserList;

/**
 * Created by Dereck on 3/5/2015.
 */
public abstract class PredictionAlgorithm {
    public abstract RatingList getPrediction(UserList userList, MovieList movieList, RatingList ratingList, RatingList predRatings);
}
