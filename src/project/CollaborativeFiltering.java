package project;

import no.uib.cipr.matrix.sparse.LinkedSparseMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import ti2736c.MovieList;
import ti2736c.Rating;
import ti2736c.RatingList;
import ti2736c.UserList;

import java.util.Collections;

/**
 * Created by Chris on 30-3-2015.
 */
public class CollaborativeFiltering extends PredictionAlgorithm{


    @Override
    public RatingList getPrediction(UserList userList, MovieList movieList, RatingList ratingList, RatingList predRatings) {
        RealMatrix utility = UtilityMatrix.createMatrix(userList, movieList, ratingList);
        RealMatrix distances = UtilityMatrix.computePearsonCorrelation(utility);
        // rating: Movie, User, Rating
        for (int i1 = 0; i1 < predRatings.size(); i1++) {
            Rating r = predRatings.get(i1);
            double[] row = distances.getRow(r.getUser().getIndex());
            double max = -1.0;
            int index = -1;
            for (int i = 0; i < row.length; i++) {
                if (row[i] > max) {
                    max = row[i];
                    index = i;
                }
            }
            r.setRating(distances.getEntry(i1,index));
        }

        return predRatings;
    }
}
