package project.omdb;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.io.IOUtils;
import project.PredictionAlgorithm;
import ti2736c.*;
import ti2736c.Movie;
import ti2736c.MovieList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

/**
 * Created by Dereck on 3/16/2015.
 */
public class OMDBMovieAlgorithm extends PredictionAlgorithm {

    /**
     * Does a prediction based on properties of a movie.
     * Uses the OMDB database to get the properties of a movie.
     */
    @Override
    public RatingList getPrediction(UserList userList, MovieList movieList, RatingList ratingList, RatingList predRatings) {
        //getAllMoviesFromOMDB(movieList, 0);
        getAllMoviesFromOMDB(movieList, 0);
        /*for (Rating rating : predRatings) {
            Movie lookup = rating.getMovie();
            try {
                //project.omdb.Movie apiMovie = API.searchMovie(lookup);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        return null;
    }

    public void checkAndWriteMovie(int to, String name) throws Exception {
        String result = API.doCall("http://www.omdbapi.com/?t=" + API.getSearchTitle(name) + "&y=&plot=short&r=json");
        System.out.println("Result " + result);
        if (result.contains("\"Error\"")) {
            System.out.println("OMDBMovieAlgorithm: Error " + name);
        } else {
            FileWriter fw = new FileWriter("data/omdb/" + to);
            fw.write(result);
            fw.flush();
            fw.close();
        }
    }

    public void getAllMoviesFromOMDB(MovieList movieList, int start) {
        for (int i = start; i < movieList.size(); i++) {
            try {
                Movie movie = movieList.get(i);
                File writeTo = new File("data/omdb/" + i);
                if (writeTo.exists()) continue;
                else checkAndWriteMovie(movie.getIndex(), movie.getTitle());
            } catch (Exception e) {
                System.out.println("OMDBMovieAlgorithm: Download stopped at " + i);
                e.printStackTrace();
                break;
            }
        }
    }
}
