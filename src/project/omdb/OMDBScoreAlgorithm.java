package project.omdb;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

import project.BeforeData;
import project.PredictionData;
import project.RatingLoopRunnable;
import ti2736c.Rating;
import ti2736c.RatingList;

/**
 * Created by Dereck on 3/16/2015.
 */
public class OMDBScoreAlgorithm extends OMDBPredictionAlgorithm {
    List<DescriptiveStatistics> userStatistics = new ArrayList<>();

    public OMDBScoreAlgorithm() {
        super("OMDBMovieAlgorithm");

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

    /**
     * Does a prediction based on the IMDB rating of a movie
     * Uses the OMDB database to get the properties of a movie.
     */
    @Override
    public RatingList getPrediction(PredictionData data, RatingList predRatings) {
        writeAllMoviesFromOMDB(data.getMovies());

        DescriptiveStatistics omdbStatistics = new DescriptiveStatistics();

        List<OMDBMovie> omdbMovies = getOmdbMovies(data.getMovies().size());

        for (int i = 0; i < predRatings.size(); i++) {
            Rating rating = predRatings.get(i);
            OMDBMovie m1 = omdbMovies.get(rating.getMovie().getIndex());
            try {
                double omdbRating = Double.parseDouble(m1.getImdbRating());
                omdbStatistics.addValue(omdbRating);
            } catch (Exception e) {
            }
        }

        double avgOmdbScore = omdbStatistics.getMean();
        System.out.println("avgOmdbScore = " + avgOmdbScore);
        for (int i = 0; i < predRatings.size(); i++) {
            Rating rating = predRatings.get(i);
            OMDBMovie m1 = omdbMovies.get(rating.getMovie().getIndex());
            try {
                double omdbBias = (Double.parseDouble(m1.getImdbRating()) - avgOmdbScore);
                System.out.println("omdbBias = " + omdbBias);
                double predictedRating = omdbBias / 2 + userStatistics.get(rating.getUser().getIndex()).getMean();
                rating.setRating(predictedRating);
            } catch (Exception e) {
                rating.setRating(null);
            }
        } return predRatings;
    }

}
