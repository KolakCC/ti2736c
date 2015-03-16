package project;

import no.uib.cipr.matrix.sparse.LinkedSparseMatrix;
import ti2736c.MovieList;
import ti2736c.Rating;
import ti2736c.RatingList;
import ti2736c.UserList;

/**
 * Utility matrix containing the ratings of users (rows) on movies(columns)
 */
public class UtilityMatrix {
    public LinkedSparseMatrix createMatrix(UserList users, MovieList movies, RatingList ratings) {
        // rows are users
        int rows = users.size();
        // cols are movies
        int cols = movies.size();

        LinkedSparseMatrix utility = new LinkedSparseMatrix(rows, cols);
        for(Rating r: ratings){
            utility.add(r.getUser().getIndex(), r.getMovie().getIndex(), r.getRating());
        }
        return utility;
    }
}
