package assignment1.samples;

import java.util.List;
import java.util.Set;

public class main {
	
	public static void exercise1_1() {
		// ADD CODE HERE
	}
	
	public static void exercise1_2() {
        MinHash minHash = new MinHash();
        minHash.addHashFunction(new HashFunction(1,1));
        minHash.addHashFunction(new HashFunction(3,1));

        ShingleSet shingleSet1 = new ShingleSet(1);
        shingleSet1.shingleString("ad");

        ShingleSet shingleSet2 = new ShingleSet(1);
        shingleSet2.shingleString("c");

        ShingleSet shingleSet3 = new ShingleSet(1);
        shingleSet3.shingleString("bde");

        ShingleSet shingleSet4 = new ShingleSet(1);
        shingleSet4.shingleString("acd");

        minHash.addSet(shingleSet1);
        minHash.addSet(shingleSet2);
        minHash.addSet(shingleSet3);
        minHash.addSet(shingleSet4);

        minHash.computeSignature();

        MinHash randomSignatures = new MinHash();
        randomSignatures.addSet(shingleSet1);
        randomSignatures.addSet(shingleSet2);
        randomSignatures.addSet(shingleSet3);
        randomSignatures.addSet(shingleSet4);
        randomSignatures.addRandomHashFunctions(100);
		exercise1_3(randomSignatures);
	}
	
	public static void exercise1_3(MinHash mh) {
        Set<List<Integer>> candidates = LSH.computeCandidates(mh.computeSignature(),5,5);
        for (List<Integer> candidate : candidates) {
            int i1 = candidate.get(0);
            int i2 = candidate.get(1);
            if (mh.getSet(i1).jaccardDistance(mh.getSet(i2)) < 0.5) {
                System.out.println(i1 + " - " + i2);
            }
        }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// exercise 1.1
		exercise1_1();
		
		// exercise 1.2
		exercise1_2();
	}

}
