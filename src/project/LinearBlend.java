package project;

import ti2736c.Rating;
import ti2736c.RatingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dereck on 3/5/2015.
 */
public class LinearBlend {
    public RatingList blend(List<RatingList> ratingLists) {
        RatingList result = new RatingList();
        for (int ratingIndex = 0; ratingIndex < ratingLists.get(0).size(); ratingIndex++) {
            Rating firstRating = ratingLists.get(0).get(ratingIndex);
            double rating = 0;
            for (RatingList list : ratingLists) {
                rating += list.get(ratingIndex).getRating();
            }
            rating = rating / (double) ratingLists.get(0).size();
            result.add(new Rating(firstRating.getUser(), firstRating.getMovie(), rating));
        }
        return result;
    }
}
