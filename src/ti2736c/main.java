package ti2736c;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import project.LinearBlend;
import project.PredictionData;
import project.RatingLoopRunnable;
import project.WeightedPrediction;
import project.WeightedRatingList;
import project.omdb.OMDBScoreAlgorithm;

public class main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        // Create user list, movie list, and list of ratings
        UserList userList = new UserList();
        userList.readFile("data/users.csv");
        MovieList movieList = new MovieList();
        movieList.readFile("data/movies.csv");
        RatingList ratings = new RatingList();
        ratings.readFile("data/ratings.csv", userList, movieList);

        // Read list of ratings we need to predict
        RatingList predRatings = new RatingList();
        predRatings.readFile("data/predictions.csv", userList, movieList);

        // Perform rating predictions
        RatingList ratings1 = predictRatings(userList, movieList, ratings, predRatings);

        // Write result file
        ratings1.writeResultsFile("outputs/submission " + getTimestamp() + ".csv");
    }

    public static RatingList predictRatings(UserList userList, MovieList movieList, RatingList ratingList, RatingList predRatings) {
        List<WeightedPrediction> algorithms = new ArrayList<>();
        //algorithms.add(new MeanAlgorithm());
        //algorithms.add(new OMDBMovieAlgorithm());
        //algorithms.add(new CollaborativeFilteringPredictionAlgorithm());
        //algorithms.add(new CachedPredictionAlgorithm("outputs/CollaborativeFilteringPredictionAlgorithm 04-01 21-20.csv"));
        //algorithms.add(new RandomRatingPredictionAlgorithm());
        //algorithms.add(new OMDBItemItemPredictionAlgorithm());
        //algorithms.add(new WeightedPrediction(1d, new CachedPredictionAlgorithm("outputs/OMDBItemItemPredictionAlgorithm 04-07 15-45.csv")));
        //algorithms.add(new UserMeanPredictionAlgorithm());

        //algorithms.add(new WeightedPrediction(1d, new CachedPredictionAlgorithm("outputs/blend1/colla.csv")));
        //algorithms.add(new WeightedPrediction(1d, new CachedPredictionAlgorithm("outputs/blend1/itemitem.csv")));

        //algorithms.add(new WeightedPrediction(1d, new CollaborativeFilteringPredictionAlgorithm(-1)));
        algorithms.add(new WeightedPrediction(1d, new OMDBScoreAlgorithm()));



        for (WeightedPrediction pa : algorithms) {
            if (pa.getAlg().getBeforeRunnable() != null) {
                pa.getAlg().getBeforeRunnable().run(userList.size()+1, movieList.size()+1, ratingList.size()+1);
            }
        }

        List<RatingLoopRunnable> runnables = new ArrayList<>();
        for (WeightedPrediction pa : algorithms) {
            if (pa.getAlg().getRunnable() != null) {
                runnables.add(pa.getAlg().getRunnable());
            }
        }

        PredictionData data = new PredictionData(userList, movieList, ratingList, runnables);

        LinearBlend blend = new LinearBlend(data, predRatings);
        blend.setRound(false);
        blend.setRoundToTenths(true);

        List<WeightedRatingList> toBlend = new ArrayList<>();
        for (WeightedPrediction pa : algorithms) {
            System.out.println("Running algorithm " + pa.getClass().getName());
            RatingList result = pa.getAlg().getPrediction(data, predRatings);
            if (!pa.getAlg().getName().startsWith("CachedPredictionAlgorithm")) {
                result.writeResultsFile("outputs/" + pa.getAlg().getName() + " " + getTimestamp() + ".csv");
            }
            toBlend.add(new WeightedRatingList(result, pa.getWeight()));
            System.out.println("Finished algorithm " + pa.getAlg().getClass().getName() + ", size = " + result.size());
        }
        return blend.blend(toBlend);
    }

    static String getTimestamp() {
        return new SimpleDateFormat("MM-dd HH-mm").format(new Date());
    }
}

