package project;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import ti2736c.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility matrix containing the ratings of users (rows) on movies(columns)
 */
public class UtilityMatrix {
    public static RealMatrix createMatrix(List<User> users, List<Movie> movies, List<Rating> ratings) {
        // rows are users
        int cols = users.size() + 1;
        // cols are movies
        int rows = movies.size() + 1;

        RealMatrix utility = new OpenMapRealMatrix(rows, cols);
        for (Rating r : ratings) {
            utility.addToEntry(r.getMovie().getIndex(), r.getUser().getIndex(), r.getRating());
        }
        return utility;
    }

    public static RealMatrix computePearsonCorrelation(RealMatrix lsm) {
        return new amatrix(lsm).getCorrelationMatrix();
    }
}

class amatrix extends PearsonsCorrelation {
    public amatrix(RealMatrix r) {
        super(r);
    }

    public RealMatrix computeCorrelationMatrix(RealMatrix matrix) {
        int nVars = matrix.getColumnDimension();
        BlockRealMatrix outMatrix = new BlockRealMatrix(nVars, nVars);

        for(int i = 0; i < nVars; ++i) {
            System.out.println("i = " + i + " < " + nVars);
            for(int j = 0; j < i; ++j) {
                //System.out.println("j = " + j);
                double corr = super.correlation(matrix.getColumn(i), matrix.getColumn(j));
                outMatrix.setEntry(i, j, corr);
                outMatrix.setEntry(j, i, corr);
            }

            outMatrix.setEntry(i, i, 1.0D);
        }

        return outMatrix;
    }

}
