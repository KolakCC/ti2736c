package ti2736c;

public class Rating {

    User user;
    Movie movie;
    Double rating;

    public Rating(User _user, Movie _movie, int _rating) {
        this.user = _user;
        this.movie = _movie;
        this.rating = (double) _rating;
    }

    public Rating(User _user, Movie _movie, Double _rating) {
        this.user = _user;
        this.movie = _movie;
        this.rating = _rating;
    }


    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double _rating) {
        this.rating = _rating;
    }


    @Override
    public String toString() {
        return "Rating{" +
                "user=" + user.getIndex() +
                ", movie=" + movie.getTitle() +
                ", rating=" + rating +
                '}';
    }
}
