package project.omdb;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import project.PredictionAlgorithm;
import ti2736c.*;
import ti2736c.Movie;
import ti2736c.MovieList;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by Dereck on 3/16/2015.
 */
public class OMDBMovieAlgorithm extends PredictionAlgorithm {
    Gson gson = new Gson();

    //Looks at n closest items
    final int n = 2;

    /**
     * Does a prediction based on the IMDB rating of a movie
     * Uses the OMDB database to get the properties of a movie.
     */
    @Override
    public RatingList getPrediction(UserList userList, MovieList movieList, RatingList ratingList, RatingList predRatings) {
        getAllMoviesFromOMDB(movieList);

        RatingList ret = new RatingList(predRatings.size());

        List<OMDBMovie> omdbMovies = new ArrayList<>();
        File[] files = new File("data/omdb").listFiles();
        for (File file : files) {
            try {
                OMDBMovie movie = gson.fromJson(FileUtils.readFileToString(file), OMDBMovie.class);
                movie.setOurID(Integer.parseInt(file.getName()));
                omdbMovies.add(movie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Rating rating : predRatings) {
            OMDBMovie omdbMovieToPredict = null;
            for (OMDBMovie movie : omdbMovies) {
                if (movie.getOurID() == rating.getMovie().getIndex()) {
                    omdbMovieToPredict = movie;
                    break;
                }
            }

            Rating predicted = new Rating(rating.getUser(), rating.getMovie(), null);

            if (omdbMovieToPredict == null) {
                System.out.println("OMDBMovieAlgorithm: Could not find movie with id " + rating.getMovie().getIndex());
                predicted.setRating(null);
            } else {
                try {
                    double predictedRating = Double.parseDouble(omdbMovieToPredict.getImdbRating()) / 2;
                    predicted.setRating(predictedRating);
                } catch (Exception e) {
                    predicted.setRating(null);
                }
            }
            ret.add(predicted);
        }
        return ret;
    }

    public File getFile(Movie movie) {
        return new File("data/omdb/" + movie.getIndex());
    }

    public void checkAndWriteMovie(File to, String name) throws Exception {
        String search = API.getSearchTitle(name);
        String result = API.doCall("http://www.omdbapi.com/?t=" + search + "&y=&plot=short&r=json");
        System.out.println("Result " + result);
        if (result.contains("\"Error\"")) {
            System.out.println("OMDBMovieAlgorithm: " + to.getName() + " - " + search + "\n Error " + name);
        } else {
            FileWriter fw = new FileWriter(to);
            fw.write(result);
            fw.flush();
            fw.close();
        }
    }

    public void getAllMoviesFromOMDB(MovieList movieList) {
        for (int i = 0; i < movieList.size(); i++) {
            try {
                Movie movie = movieList.get(i);
                File writeTo = getFile(movie);
                if (writeTo.exists()) continue;
                else checkAndWriteMovie(writeTo, movie.getTitle());
            } catch (Exception e) {
                System.out.println("OMDBMovieAlgorithm: Download stopped at " + i);
                e.printStackTrace();
                break;
            }
        }
    }
}
