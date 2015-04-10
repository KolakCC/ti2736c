package project.omdb;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import project.PredictionAlgorithm;
import ti2736c.Movie;

/**
 * Created by Dereck on 01/04/2015.
 */
abstract public class OMDBPredictionAlgorithm extends PredictionAlgorithm {
    Gson gson = new Gson();

    public OMDBPredictionAlgorithm(String name) {
        super(name);
    }

    public File getFile(Movie movie) {
        return new File("data/omdb/" + movie.getIndex());
    }

    public void writeAllMoviesFromOMDB(List<Movie> movieList) {
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

    public List<OMDBMovie> getOmdbMovies(int size) {
        List<OMDBMovie> omdbMovies = new ArrayList<>();
        for (int i = 0; i < size+1; i++) {
            omdbMovies.add(null);
        }

        File[] files = new File("data/omdb").listFiles();
        for (File file : files) {
            try {
                OMDBMovie movie = gson.fromJson(FileUtils.readFileToString(file), OMDBMovie.class);
                int id = Integer.parseInt(file.getName());
                omdbMovies.set(id, movie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return omdbMovies;
    }
}
