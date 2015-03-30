package project;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import ti2736c.MovieList;
import ti2736c.Rating;
import ti2736c.RatingList;
import ti2736c.UserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dereck on 3/5/2015.
 */
public class LinearBlend {
    RatingList mean;
    public LinearBlend(UserList userList, MovieList movieList, RatingList ratingList, RatingList predRatings) {
        mean = new MeanAlgorithm().getPrediction(userList, movieList, ratingList, predRatings);
    }

    public RatingList blend(List<RatingList> ratingLists) {
        RatingList result = new RatingList();
        int firstSize = ratingLists.get(0).size();
        for (int ratingIndex = 0; ratingIndex < ratingLists.get(0).size(); ratingIndex++) {
            Rating firstRating = ratingLists.get(0).get(ratingIndex);
            SummaryStatistics summaryStatistics = new SummaryStatistics();

            for (RatingList list : ratingLists) {
                if (list.size() != firstSize) throw new RuntimeException("Rating list size is not the same");
                Double rating = list.get(ratingIndex).getRating();
                if (rating != null) {
                    if (rating > 5) {
                        throw new RuntimeException("Rating can't be >5");
                    }
                    summaryStatistics.addValue(rating);
                }
            }
            double blended = summaryStatistics.getMean();
            if (blended == Double.NaN) {
                blended = mean.get(ratingIndex).getRating();
            }
            Rating rating = new Rating(firstRating.getUser(), firstRating.getMovie(), blended);
            if (rating.getRating() == null) {
                throw new RuntimeException("Rating is null");
            }
            result.add(rating);
        }
        return result;
    }
}
