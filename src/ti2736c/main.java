package ti2736c;

import project.LinearBlend;
import project.MeanAlgorithm;
import project.omdb.OMDBMovieAlgorithm;

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
        predictRatings(userList, movieList, ratings, predRatings);

        // Write result file
        predRatings.writeResultsFile("submission.csv");
    }

    public static RatingList predictRatings(UserList userList,
                                            MovieList movieList, RatingList ratingList, RatingList predRatings) {
        LinearBlend blend = new LinearBlend();
        List<RatingList> toBlend = new ArrayList<RatingList>();
        toBlend.add(new MeanAlgorithm().getPrediction(userList, movieList, ratingList, predRatings));
        toBlend.add(new OMDBMovieAlgorithm().getPrediction(userList, movieList, ratingList, predRatings));
        RatingList result = blend.blend(toBlend);
        return result;
    }
}
