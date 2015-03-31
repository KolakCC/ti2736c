package project;

import no.uib.cipr.matrix.sparse.LinkedSparseMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import ti2736c.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CollaborativeFilteringPredictionAlgorithm extends PredictionAlgorithm {

    @Override
    public RatingList getPrediction(UserList userList, MovieList movieList, RatingList ratingList, RatingList predRatings) {

        ArrayList<Double> averages = new ArrayList<>(userList.size());
        ArrayList<Integer> counts = new ArrayList<>(userList.size());

        ArrayList<List<Rating>> ratingsGivenByUser = new ArrayList<>(userList.size());
        ArrayList<List<Rating>> ratingsGivenToMovie = new ArrayList<>(movieList.size());


        for (int i = 0; i <= userList.size(); i++) {
            averages.add(0d);
            counts.add(0);

            ratingsGivenByUser.add(new ArrayList<Rating>());
        }

        for (int i = 0; i <= movieList.size(); i++) {
            ratingsGivenToMovie.add(new ArrayList<Rating>());
        }

        System.out.println("CollaborativeFilteringPredictionAlgorithm: Rating sums");
        for (Rating r : ratingList) {
            //biases
            int user = r.getUser().getIndex();
            double newSum = averages.get(user) + r.getRating();
            int newCount = counts.get(user) + 1;

            averages.set(r.getUser().getIndex(), newSum);
            counts.set(r.getUser().getIndex(), newCount);

            //map users -> ratings
            List<Rating> currentRatings = ratingsGivenByUser.get(user);
            currentRatings.add(r);

            int movie = r.getMovie().getIndex();
            List<Rating> movieRatingList = ratingsGivenToMovie.get(movie);
            movieRatingList.add(r);
        }

        System.out.println("CollaborativeFilteringPredictionAlgorithm: Rating averages");
        for (int i = 0; i < averages.size(); i++) {
            Double sum = averages.get(i);
            int count = counts.get(i);
            averages.set(i, sum / (double) count);
        }

        System.out.println("CollaborativeFilteringPredictionAlgorithm: Making predictions amount = " + predRatings.size());
        /**
         * Problemen: i = 161 geeft een rating van 86
         *            i =  68 geeft een rating van NaN
         */
        for (int i = 0; i < predRatings.size(); i++) {
        /*int i = 161;
        {*/
            Rating toPredict = predRatings.get(i);
            User toPredictUser = toPredict.getUser();
            int a = toPredictUser.getIndex();

            double aAverage = averages.get(a);
            DescriptiveStatistics astat = new DescriptiveStatistics();
            for (Rating d : ratingsGivenByUser.get(a)) {
                astat.addValue(d.getRating());
            }
            double aDeviation = astat.getStandardDeviation();

            double weightedBiasSum = 0;
            double totalWeight = 0;
            List<Rating> otherRatingsOfThisMovie = ratingsGivenToMovie.get(toPredict.getMovie().getIndex());
            List<Rating> otherRatingsOfThisMovieSampled = getRandomSelectionOfList(otherRatingsOfThisMovie, 200);
            //System.out.println("CollaborativeFilteringPredictionAlgorithm: other ratings of this movie = " + otherRatingsOfThisMovie.size());
            for (Rating otherRating : otherRatingsOfThisMovieSampled) {
                int u = otherRating.getUser().getIndex();
                double rating = otherRating.getRating();
                double userAverage = averages.get(u);
                double uBias = rating - userAverage;
                double weight = 0;

                double topPart = 0d;

                //elke film die ze samen gezien hebben
                List<Rating> filteredARatings = getRandomSelectionOfList(ratingsGivenByUser.get(a), 100);
                //System.out.println("CollaborativeFilteringPredictionAlgorithm: get every movie that they both have seen = " + filteredARatings.size());

                for (Rating aRating : filteredARatings) {
                    Double overeenkomst = null;
                    Movie m = aRating.getMovie();
                    //System.out.println("CollaborativeFilteringPredictionAlgorithm: get every movie that they both have seen inner = " + ratingsGivenByUser.get(u).size());
                    for (Rating uRating : ratingsGivenByUser.get(u)) {
                        if (m.equals(uRating.getMovie())) {
                            overeenkomst = uRating.getRating();
                            break;
                        }
                    }
                    if (overeenkomst == null) continue;
                    double increase =(aRating.getRating() - aAverage) * (overeenkomst - userAverage);
                    topPart += increase;
                    System.out.printf("increase = (aRating.getRating() - aAverage) * (overeenkomst - userAverage) ->  %f = (%f - %f) * (%f - %f) %n",
                            increase, aRating.getRating(), aAverage, overeenkomst, userAverage);
                }

                DescriptiveStatistics ustat = new DescriptiveStatistics();
                for (Rating d : ratingsGivenByUser.get(u)) {
                    ustat.addValue(d.getRating());
                }
                double uDeviation = ustat.getStandardDeviation();
                double bottomPart = aDeviation * uDeviation;

                weight = topPart / bottomPart;
                if (weight == Double.NaN) weight = 1;
                System.out.printf("weight = topPart / bottomPart ->  %f = (%f / %f) %n", weight, topPart, bottomPart);
                totalWeight += weight;

                double weightedBias = uBias * weight;
                System.out.printf("weightedBias = uBias * weight ->  %f = (%f * %f) %n", weightedBias, uBias, weight);
                weightedBiasSum += weightedBias;
            }
            double prediction = aAverage + (weightedBiasSum / totalWeight);
            System.out.printf("aAverage + (weightedBiasSum / totalWeight) ->  %f + (%f / %f) %n", aAverage, weightedBiasSum, totalWeight);
            toPredict.setRating(prediction);

            System.out.printf("CollaborativeFilteringPredictionAlgorithm: Predicted %f for %d/%d %n", prediction, i, predRatings.size());

        }
        return predRatings;
    }

    Random random = new Random();
    <T> List<T>  getRandomSelectionOfList(List<T> list, int n) {
        /*if (list.size() < n) return list;
        List<T> newList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            newList.add(list.get(random.nextInt(list.size())));
        }
        return newList;*/
        return list;
    }
}
