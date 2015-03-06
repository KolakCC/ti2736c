import java.util.*;
import java.util.Map.Entry;


public class NearestNeighbour {
    /**
     * List of training vectors.
     */
    Dataset dataset;

    /**
     * Constructor.
     */
    public NearestNeighbour() {
        dataset = new Dataset();
    }

    /**
     * Reads in the data from a text file.
     *
     * @param fileName
     */
    public void readData(String fileName) {
        dataset.readData(fileName, false);
    }

    /**
     * @return
     */
    public Dataset getDataset() {
        return dataset;
    }

    /**
     * Classifies a query. Finds the k nearest neighbours and scales them if necessary.
     *
     * @param features The query features.
     * @param k        The number of neighbours to select.
     * @return Returns the label assigned to the query.
     */
    public int predict(List<Double> features, int k) {
        //
        // 1. Calculate the distance of the object to each element in the dataset.
        ArrayList<Measurement> measurements = new ArrayList<Measurement>();
        for (FeatureVector fv : dataset) {
            measurements.add(new Measurement(fv, fv.distance(features)));
        }
        // 2. Sort them by distance, smallest distances first.
        Collections.sort(measurements);
        // 3. Select k of the nearest objects.
        while (measurements.size() > k) {
            measurements.remove(measurements.size() - 1);
        }
        // 4. Calculate which label has the highest majority vote.
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (Measurement m : measurements) {
            int i = m.getFeatureVector().getLabel();
            Integer count = map.get(i);
            map.put(i, count != null ? count + 1 : 0);
        }
        Integer popular = Collections.max(map.entrySet(),
                new Comparator<Entry<Integer, Integer>>() {
                    @Override
                    public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                }).getKey();

        return popular;
    }
}
