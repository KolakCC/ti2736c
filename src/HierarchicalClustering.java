import java.util.ArrayList;
import java.util.List;

public class HierarchicalClustering {
	/**
	 * The number of clusters to detect.
	 */
	private int k;
	
	/**
	 * The original unclustered data.
	 */
	private Cluster data;
	
	/**
	 * A collection of clusters.
	 */
	private List<Cluster> clusters;
	
	/**
	 * Constructor.
	 * @param k The number of clusters to detect.
	 * @param fileName The filename at which the data is stored.
	 */
	public HierarchicalClustering(int k, String fileName) {
		this.k = k;
		clusters = new ArrayList<Cluster>();
		
		data = new Cluster();
		
		readData(fileName);
	}
	
	/**
	 * Reads in data from a filename.
	 * @param fileName The filename that needs to be read.
	 */
	public void readData(String fileName) {
		data.readData(fileName);
		
		for (FeatureVector fv: data) {
			Cluster c = new Cluster();
			c.add(fv);
			clusters.add(c);
		}
	}
	
	/**
	 * @return The unclustered data.
	 */
	public Cluster getData() {
		return data;
	}
	
	/**
	 * @return The clusters that have been computed so far.
	 */
	public List<Cluster> getClusters() {
		return clusters;
	}
	
	/**
	 * 
	 * @return Returns the number of clusters in the list of clusters.
	 */
	public int getClusterSize() {
		return clusters.size();
	}
	
	/**
	 * 
	 * @param i Index of the cluster that is to be retrieved.
	 * @return Returns the cluster belonging to index i.
	 */
	public Cluster getCluster(int i) {
		return clusters.get(i);
	}
	
	/**
	 * Performs one update step of the algorithm.
	 */
	public void update() {
		double smallestDistance = Double.MAX_VALUE;
		int smallestI = -1;
		int smallestJ = -1;
		for (int i = 0; i < clusters.size(); i++) {
			Cluster c1 = clusters.get(i);
			for (int j = 0; j < clusters.size(); j++) {
				Cluster c2 = clusters.get(j);
				double distance = c1.meanDistanceTo(c2);
				if (distance < smallestDistance) {
					smallestI = i;
					smallestJ = j;
					smallestDistance = distance;
				}
			}
		}

		Cluster c1 = clusters.get(smallestI);
		Cluster c2 = clusters.get(smallestJ);
		clusters.remove(c1);
		c2.addAll(c1);
	}
}
