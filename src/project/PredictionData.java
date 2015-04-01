package project;

import java.util.ArrayList;
import java.util.List;

import ti2736c.Movie;
import ti2736c.MovieList;
import ti2736c.Rating;
import ti2736c.RatingList;
import ti2736c.User;
import ti2736c.UserList;

public class PredictionData {
    List<User> userList;
    List<Movie> movieList;
    List<Rating> ratingList;
    RatingList predRatings;

    List<List<Rating>> ratingMatrix;

    public PredictionData(UserList userList, MovieList movieList, RatingList ratingList, List<RatingLoopRunnable> inRatingsLoopRunnables) {
        this.userList = userList;
        this.movieList = movieList;
        this.ratingList = ratingList;

        ratingMatrix = new ArrayList<>();
        for (int i = 0; i < this.userList.size()+1; i++) {
            List<Rating> userRatings = new ArrayList<>();
            for (int i1 = 0; i1 < this.userList.size()+1; i1++) {
                userRatings.add(null);
            }
            ratingMatrix.add(userRatings);
        }

        for (Rating rating : ratingList) {
            ratingMatrix.get(rating.getUser().getIndex()).set(rating.getMovie().getIndex(), rating);
            for (RatingLoopRunnable runnable : inRatingsLoopRunnables) {
                runnable.run(rating);
            }
        }
    }

    public Rating getUserRatingOfMovie(User u, Movie m) {
        return getUserRatingOfMovie(u.getIndex(), m.getIndex());
    }

    public Rating getUserRatingOfMovie(int u, int m) {
        return ratingMatrix.get(u).get(m);
    }

    public List<Rating> getUserRatings(User u) {
        return getUserRatings(u.getIndex());
    }

    public List<Rating> getUserRatings(int u) {
        List<Rating> row = ratingMatrix.get(u);
        return row;
    }

    public List<Rating> getRatingsOfMovie(Movie m) { return getRatingsOfMovie(m.getIndex()); }

    public List<Rating> getRatingsOfMovie(int m) {
        List<Rating> column = new ArrayList<Rating>();
        for (int i = 0; i < userList.size(); i++) {
            Rating r = ratingMatrix.get(i).get(m);
            column.add(r);
        }
        return column;
    }

    public User getUser(int u) {
        return userList.get(u);
    }

    public List<User> getUsers() {
        return userList;
    }

    public Movie getMovie(int m) {
        return movieList.get(m);
    }
    public List<Movie> getMovies() {
        return movieList;
    }


}
