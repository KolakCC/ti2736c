package project;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import ti2736c.MovieList;
import ti2736c.Rating;
import ti2736c.RatingList;
import ti2736c.UserList;

import java.util.ArrayList;

/**
 * Utility matrix containing the ratings of users (rows) on movies(columns)
 */
public class UtilityMatrix {
    public static RealMatrix createMatrix(UserList users, MovieList movies, RatingList ratings) {
        // rows are users
        int rows = users.size();
        // cols are movies
        int cols = movies.size();

        RealMatrix utility = new Array2DRowRealMatrix(rows, cols);
        for(Rating r: ratings){
           utility.addToEntry(r.getUser().getIndex(), r.getMovie().getIndex(), r.getRating());
        }
        return utility;
    }

    public static RealMatrix computePearsonCorrelation(RealMatrix lsm){
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation(lsm);
        return pearsonsCorrelation.getCorrelationMatrix();

    }
}
