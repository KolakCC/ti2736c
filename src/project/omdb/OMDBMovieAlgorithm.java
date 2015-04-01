package project.omdb;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import project.PredictionAlgorithm;
import project.PredictionData;
import ti2736c.Movie;
import ti2736c.Rating;
import ti2736c.RatingList;

/**
 * Created by Dereck on 3/16/2015.
 */
public class OMDBMovieAlgorithm extends PredictionAlgorithm {
    Gson gson = new Gson();

    //Looks at n closest items
    final int n = 2;

    public OMDBMovieAlgorithm() {
        super("OMDBMovieAlgorithm");
    }

    /**
     * Does a prediction based on the IMDB rating of a movie
     * Uses the OMDB database to get the properties of a movie.
     */
    @Override
    public RatingList getPrediction(PredictionData data, RatingList predRatings) {
        getAllMoviesFromOMDB(data.getMovies());

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

            if (omdbMovieToPredict == null) {
                System.out.println("OMDBMovieAlgorithm: Could not find movie with id " + rating.getMovie().getIndex());
                rating.setRating(null);
            } else {
                try {
                    double predictedRating = Double.parseDouble(omdbMovieToPredict.getImdbRating()) / 2;
                    rating.setRating(predictedRating);
                } catch (Exception e) {
                    rating.setRating(null);
                }
            }

        }
        return predRatings;
    }

    public File getFile(Movie movie) {
        return new File("data/omdb/" + movie.getIndex());
    }

    public void getAllMoviesFromOMDB(List<Movie> movieList) {
        for (int i = 0; i < movieList.size(); i++) {
            try {
                Movie movie = movieList.get(i);
                File writeTo = getFile(movie);
                if (writeTo.exists()) continue;
                else {
                    String search = API.getSearchTitle(movie.getTitle());
                    String result = API.doCall("http://www.omdbapi.com/?t=" + search + "&y=&plot=short&r=json");
                    System.out.println("Result " + result);
                    if (result.contains("\"Error\"")) {
                        System.out.println("OMDBMovieAlgorithm: " + writeTo.getName() + " - " + search + "\n Error " + movie.getTitle());
                    } else {
                        FileWriter fw = new FileWriter(writeTo);
                        fw.write(result);
                        fw.flush();
                        fw.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("OMDBMovieAlgorithm: Download stopped at " + i);
                e.printStackTrace();
                break;
            }
        }
    }
}
