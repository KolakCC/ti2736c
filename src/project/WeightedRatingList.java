package project;

import ti2736c.RatingList;

/**
 * Created by Dereck on 07/04/2015.
 */
public class WeightedRatingList {
    double weight;
    RatingList ratingList;

    public WeightedRatingList(RatingList ratingList, double weight) {
        this.ratingList = ratingList;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public RatingList getRatingList() {
        return ratingList;
    }
}
