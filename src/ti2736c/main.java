package ti2736c;

import project.CollaborativeFilteringPredictionAlgorithm;
import project.LinearBlend;
import project.MeanAlgorithm;
import project.PredictionAlgorithm;
import project.omdb.OMDBMovieAlgorithm;

import javax.sql.rowset.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        ratings1.writeResultsFile("submission.csv");
    }

    public static RatingList predictRatings(UserList userList,
                                            MovieList movieList, RatingList ratingList, RatingList predRatings) {
        LinearBlend blend = new LinearBlend(userList, movieList, ratingList, predRatings);

        List<PredictionAlgorithm> algorithms = new ArrayList<>();
        //algorithms.add(new MeanAlgorithm());
        //algorithms.add(new OMDBMovieAlgorithm());
        algorithms.add(new CollaborativeFilteringPredictionAlgorithm());

        List<RatingList> toBlend = new ArrayList<>();

        for (PredictionAlgorithm pa: algorithms) {
            System.out.println("Running algorithm " + pa.getClass().getName());
            //RatingList copyOfPredictRatings = new RatingList(predRatings);
            //toBlend.add(pa.getPrediction(userList, movieList, ratingList, copyOfPredictRatings));
            toBlend.add(pa.getPrediction(userList, movieList, ratingList, predRatings));
        }
        return blend.blend(toBlend);
    }
}
