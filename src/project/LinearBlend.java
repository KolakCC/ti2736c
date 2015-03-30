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
        int firstSize = ratingLists.get(0).size();
        for (int ratingIndex = 0; ratingIndex < ratingLists.get(0).size(); ratingIndex++) {
            Rating firstRating = ratingLists.get(0).get(ratingIndex);
            double rating = 0;
            for (RatingList list : ratingLists) {
                if (list.size() != firstSize) throw new RuntimeException("Rating list size is not the same");
                rating += list.get(ratingIndex).getRating();
            }
            rating = rating / (double) ratingLists.get(0).size();
            result.add(new Rating(firstRating.getUser(), firstRating.getMovie(), rating));
        }
        return result;
    }
}
