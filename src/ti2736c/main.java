package ti2736c;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import project.BeforeData;
import project.LinearBlend;
import project.PredictionAlgorithm;
import project.PredictionData;
import project.RandomRatingPredictionAlgorithm;
import project.RatingLoopRunnable;

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
        List<PredictionAlgorithm> algorithms = new ArrayList<>();
        //algorithms.add(new MeanAlgorithm());
        //algorithms.add(new OMDBMovieAlgorithm());
        //algorithms.add(new CachedPredictionAlgorithm("outputs/OMDBMovieAlgorithm 03-31 16-52.csv"));
        //algorithms.add(new CollaborativeFilteringPredictionAlgorithm());
        //algorithms.add(new CachedPredictionAlgorithm("outputs/CollaborativeFilteringPredictionAlgorithm 03-31 17-59.csv"));

        algorithms.add(new RandomRatingPredictionAlgorithm());


        List<BeforeData> beforeRunnables = new ArrayList<>();
        for (PredictionAlgorithm pa : algorithms) {
            if (pa.getBeforeRunnable() != null) {
                pa.getBeforeRunnable().run(userList.size()+1, movieList.size()+1, ratingList.size()+1);
            }
        }

        List<RatingLoopRunnable> runnables = new ArrayList<>();
        for (PredictionAlgorithm pa : algorithms) {
            if (pa.getRunnable() != null) {
                runnables.add(pa.getRunnable());
            }
        }

        PredictionData data = new PredictionData(userList, movieList, ratingList, runnables);

        LinearBlend blend = new LinearBlend(data, predRatings);
        blend.setRound(false);
        List<RatingList> toBlend = new ArrayList<>();
        for (PredictionAlgorithm pa : algorithms) {
            System.out.println("Running algorithm " + pa.getClass().getName());
            RatingList result = pa.getPrediction(data, predRatings);
            result.writeResultsFile("outputs/" + pa.getName() + " " + getTimestamp() + ".csv");
            toBlend.add(result);
            System.out.println("Finished algorithm " + pa.getClass().getName() + ", size = " + result.size());
        }
        return blend.blend(toBlend);
    }

    static String getTimestamp() {
        return new SimpleDateFormat("MM-dd HH-mm").format(new Date());
    }
}
