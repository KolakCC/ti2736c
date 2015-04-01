package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import ti2736c.RatingList;

/**
 * Created by Dereck on 31/03/2015.
 */
public class CachedPredictionAlgorithm extends PredictionAlgorithm {
    String file;

    public CachedPredictionAlgorithm(String file) {
        super("CachedPredictionAlgorithm");
        this.file = file;
    }

    @Override
    public RatingList getPrediction(PredictionData data, RatingList predRatings) {
        List<Double> fileRatings = readFile(file);
        for (int i = 0; i < predRatings.size(); i++) {
            Double rating = fileRatings.get(i);
            predRatings.get(i).setRating(rating);
        }
        return predRatings;
    }


    public List<Double> readFile(String filename) {
        List<Double> ret = new ArrayList<>();
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Id,Rating"))
                    continue;
                if (line.isEmpty()) continue;
                String[] ratingData = line.split(",");
                if (ratingData.length == 2) {
                    if (ratingData[1].equals("null")) ret.add(null);
                    else ret.add(Double.parseDouble(ratingData[1]));
                } else {
                    throw new RuntimeException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
}
