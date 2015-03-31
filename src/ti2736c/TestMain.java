package ti2736c;

import project.CollaborativeFilteringPredictionAlgorithm;

/**
 * Created by Dereck on 3/31/2015.
 */
public class TestMain {
    public static void main(String[] args) {
        UserList u = new UserList();
        u.add(new User(1, true, 0, 9));
        u.add(new User(2, true, 0, 9));
        MovieList m = new MovieList();
        m.add(new Movie(1, 1990, "tarzan"));
        m.add(new Movie(2, 1990, "tarzan 2"));
        RatingList r = new RatingList();
        r.add(new Rating(u.get(0), m.get(0), 5));
        r.add(new Rating(u.get(1), m.get(0), 5));

        r.add(new Rating(u.get(0), m.get(1), 4));

        RatingList p = new RatingList();
        p.add(new Rating(u.get(1), m.get(1), null));

        RatingList prediction = new CollaborativeFilteringPredictionAlgorithm().getPrediction(u, m, r, p);

    }
}
