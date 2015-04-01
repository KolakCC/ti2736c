package project;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.List;

import ti2736c.Rating;
import ti2736c.RatingList;

/**
 * Created by Dereck on 3/5/2015.
 */
public class LinearBlend {
    RatingList mean;
    public LinearBlend(PredictionData data, RatingList predRatings) {
        mean = new MeanAlgorithm().getPrediction(data, predRatings);
    }

    boolean round = false;
    public void setRound(boolean r) {
        round = r;
    }

    public RatingList blend(List<RatingList> ratingLists) {
        RatingList result = new RatingList();
        int firstSize = ratingLists.get(0).size();
        for (int ratingIndex = 0; ratingIndex < ratingLists.get(0).size(); ratingIndex++) {
            Rating firstRating = ratingLists.get(0).get(ratingIndex);
            SummaryStatistics summaryStatistics = new SummaryStatistics();

            for (RatingList list : ratingLists) {
                if (list.size() != firstSize) {
                    System.out.println("list.size() = " + list.size());
                    System.out.println("firstSize = " + firstSize);
                    throw new RuntimeException("Rating list size is not the same");
                }
                Double rating = list.get(ratingIndex).getRating();
                if (rating != null) {
                    summaryStatistics.addValue(rating);
                }
            }
            double blended = summaryStatistics.getMean();
            if (Double.isNaN(blended)) {
                blended = mean.get(ratingIndex).getRating();
            }

            if (blended < 1) {
                blended = 1d;
            } else if (blended > 5) {
                blended = 5d;
            } else {
                if (round)
                    blended = (double) Math.ceil(blended);
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
