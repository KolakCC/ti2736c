package assignment1.samples;

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
        randomSignatures.addRandomHashFunctions(100);
		exercise1_3(randomSignatures);
	}
	
	public static void exercise1_3(MinHash mh) {
		// ADD CODE HERE
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
