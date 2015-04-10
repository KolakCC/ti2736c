package project;

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
    boolean round10ths = false;
    public void setRound(boolean r) {
        round = r;
    }
    public void setRoundToTenths(boolean r) {round10ths = r;}

    public RatingList blend(List<WeightedRatingList> ratingLists) {
        RatingList result = new RatingList();
        int firstSize = ratingLists.get(0).getRatingList().size();
        for (int ratingIndex = 0; ratingIndex < ratingLists.get(0).getRatingList().size(); ratingIndex++) {
            Rating firstRating = ratingLists.get(0).getRatingList().get(ratingIndex);

            double weightedSum = 0d;
            double weightSum = 0d;

            for (WeightedRatingList list : ratingLists) {
                if (list.getRatingList().size() != firstSize) {
                    System.out.println("list.size() = " + list.getRatingList().size());
                    System.out.println("firstSize = " + firstSize);
                    throw new RuntimeException("Rating list size is not the same");
                }
                Double rating = list.getRatingList().get(ratingIndex).getRating();
                if (rating != null) {
                    weightSum += list.getWeight();
                    weightedSum += rating * list.getWeight();
                }
            }

            double blended = weightedSum / weightSum;

            if (Double.isNaN(blended)) {
                blended = MeanAlgorithm.CACHE;
            }

            if (blended < 1) {
                blended = 1d;
            } else if (blended > 5) {
                blended = 5d;
            } else {
                if (round10ths)
                    blended = (double) Math.round(blended * 10) / 10;
                else if (round)
                    blended = (double) Math.round(blended);
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
