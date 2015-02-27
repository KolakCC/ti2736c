package assignment1.samples;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hansgaiser
 */
public class LSH {

//    initialize buckets as a list of lists of integers
//    foreach band do
    //    foreach set do
    //    extract a column segment of length r, for this band and set, as string s
    //    add s to buckets[hash(s)]
    //    end
//    end

    /**
     * Computes the candidate pairs using the LSH technique.
     *
     * @param mhs The minhash signature object.
     * @param bs  The number of buckets to be used in the LSH table.
     * @param r   The number of rows per band to be used.
     * @return Returns a set of indices pairs that are candidate to being similar.
     */
    public static Set<List<Integer>> computeCandidates(MinHashSignature mhs, int bs, int r) {
        // assert that the number of rows can be evenly divided by r
        assert (mhs.rows() % r == 0);

        // the number of bands
        final int b = mhs.rows() / r;

        // the result
        Set<List<Integer>> candidates = new HashSet<List<Integer>>();

        // ADD CODE HERE
        List<List<Integer>> buckets = new ArrayList<List<Integer>>();
        for (int i = 0; i < bs; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < mhs.cols(); j++) {
                String s = mhs.colSegment(j, i * r, (i * r) + r);
                buckets.get(s.hashCode()%bs).add(j);
            }
        }
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < mhs.cols(); j++) {
                String s = mhs.colSegment(j, i * r, (i * r) + r);
                // add all pairs of the good bucket to the candidates
                List<Integer> bucket = buckets.get(s.hashCode() % bs);
                for (final Integer i1 : bucket) {
                    for (final Integer i2 : bucket) {
                        if (i1 < i2) {
                            candidates.add(new ArrayList<Integer>() {{ add(i1); add(i2); }});
                        }
                    }
                }
            }
        }

        return candidates;
    }

}
