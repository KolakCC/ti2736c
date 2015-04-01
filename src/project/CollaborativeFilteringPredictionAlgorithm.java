package project;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

import ti2736c.Rating;
import ti2736c.RatingList;
import ti2736c.User;

public class CollaborativeFilteringPredictionAlgorithm extends PredictionAlgorithm {
    List<DescriptiveStatistics> userStatistics = new ArrayList<>();

    public CollaborativeFilteringPredictionAlgorithm() {
        super("CollaborativeFilteringPredictionAlgorithm");
        this.setBeforeRunnable(new BeforeData() {
            @Override
            public void run(int userSize, int movieSize, int ratingSize) {
                userStatistics = new ArrayList<>(userSize);
                for (int i = 0; i < userSize; i++) {
                    userStatistics.add(new DescriptiveStatistics());
                }
            }
        });
        this.setRunnable(new RatingLoopRunnable() {
            @Override
            public void run(Rating r) {
                userStatistics.get(r.getUser().getIndex()).addValue(r.getRating());
            }
        });
    }

    @Override
    public RatingList getPrediction(PredictionData data, RatingList predRatings) {

        for (int i = 0; i < predRatings.size(); i++) {
            Rating toPredict = predRatings.get(i);
            User toPredictUser = toPredict.getUser();
            int a = toPredictUser.getIndex();

            double aAverage = userStatistics.get(a).getMean();

            double weightedBiasSum = 0;
            double totalWeight = 0;
            List<Rating> otherRatingsOfThisMovie = data.getRatingsOfMovie(toPredict.getMovie());
            //ist<Rating> otherRatingsOfThisMovieSampled = getRandomSelectionOfList(otherRatingsOfThisMovie, 20);
            //System.out.println("CollaborativeFilteringPredictionAlgorithm: other ratings of this movie = " + otherRatingsOfThisMovie.size());
            for (Rating otherRating : otherRatingsOfThisMovie) {
                if (otherRating == null) continue;
                int u = otherRating.getUser().getIndex();
                double rating = otherRating.getRating();
                double userAverage = userStatistics.get(u).getMean();
                double uBias = rating - userAverage;
                double weight = 0;

                double topPart = 0d;

                //elke film die ze samen gezien hebben
                //List<Rating> filteredARatings = getRandomSelectionOfList(ratingsGivenByUser.get(a), 20);
                List<Rating> filteredARatings = data.getUserRatings(a);
                //System.out.println("CollaborativeFilteringPredictionAlgorithm: get every movie that they both have seen = " + filteredARatings.size());

                DescriptiveStatistics astat = new DescriptiveStatistics();
                DescriptiveStatistics ustat = new DescriptiveStatistics();
                for (int movieID = 0; movieID < data.movieList.size(); movieID++) {
                    Rating aRating = data.getUserRatingOfMovie(a, movieID);
                    Rating uRating = data.getUserRatingOfMovie(a, movieID);
                    if (aRating == null || uRating == null) { //they haven't both seen it
                        continue;
                    }
                    double aBias = (aRating.getRating() - aAverage);
                    double uBiasOfIntersectMovie = (uRating.getRating() - userAverage);
                    double increase = aBias * uBiasOfIntersectMovie;
                    topPart += increase;
                    astat.addValue(aBias);
                    ustat.addValue(uBiasOfIntersectMovie);
                }
                if (astat.getN() <= 1) continue;
                double aDeviation = astat.getStandardDeviation();
                double uDeviation = ustat.getStandardDeviation();
                double bottomPart = aDeviation * uDeviation;
                //System.out.printf("bottomPart = aDeviation * uDeviation -> %f = %f * %f %n", bottomPart, aDeviation, uDeviation);

                weight = topPart / bottomPart;
                if (Double.isNaN(weight)) weight = 1;
                if (Double.isInfinite(weight)) weight = 1;
                if (weight < 0) continue;
                //System.out.printf("weight = topPart / bottomPart ->  %f = (%f / %f) %n", weight, topPart, bottomPart);
                totalWeight += weight;

                double weightedBias = uBias * weight;
                //System.out.printf("weightedBias = uBias * weight ->  %f = (%f * %f) %n", weightedBias, uBias, weight);
                weightedBiasSum += weightedBias;
            }
            double prediction = aAverage + (weightedBiasSum / totalWeight);
            //System.out.printf("aAverage + (weightedBiasSum / totalWeight) ->  %f + (%f / %f) %n", aAverage, weightedBiasSum, totalWeight);
            toPredict.setRating(prediction);

            System.out.printf("CollaborativeFilteringPredictionAlgorithm: Predicted %f for %d/%d %n", prediction, i, predRatings.size());

        }
        return predRatings;
    }
}
