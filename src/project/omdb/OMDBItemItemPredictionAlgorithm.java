package project.omdb;

import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.List;

import project.PredictionData;
import ti2736c.Rating;
import ti2736c.RatingList;

/**
 * Created by Dereck on 01/04/2015.
 */
public class OMDBItemItemPredictionAlgorithm extends OMDBPredictionAlgorithm {
    public OMDBItemItemPredictionAlgorithm() {
        super("OMDBItemItemPredictionAlgorithm");
    }

    @Override
    public RatingList getPrediction(PredictionData data, RatingList predRatings) {
        List<OMDBMovie> omdbMovies = getOmdbMovies(data.getMovies().size());
        for (int i = 0; i < predRatings.size(); i++) {
            Rating rating = predRatings.get(i);
            OMDBMovie m1 = omdbMovies.get(rating.getMovie().getIndex());

            double weightSum = 0;
            double toppart = 0d;


            List<Rating> ratingsOfOtherMovies = data.getUserRatings(rating.getUser());
            for (Rating r : ratingsOfOtherMovies) {
                if (r == null)
                    continue;
                OMDBMovie m2 = omdbMovies.get(r.getMovie().getIndex());

                double weight = calculateSimilarity(m1, m2);
                weightSum += weight;

                double num = r.getRating();
                toppart += num * weight;

            }
            if (weightSum == 0) continue;
            double predicted = toppart / weightSum;


            System.out.printf("OMDBItemItemPredictionAlgorithm: Predicted %f for %d/%d %n", predicted, i, predRatings.size());
            rating.setRating(predicted);

        }
        return predRatings;
    }

    double genreWeight = 20;
    double yearWeight = 1;
    double directorWeight = 3;

    public double calculateSimilarity(OMDBMovie m1, OMDBMovie m2) {
        double similarity = 0d;

        //If movies are the same genre, they are more similar.
        double genreIntersection = (double) CollectionUtils.intersection(Arrays.asList(m1.getGenreArray()), Arrays.asList(m2.getGenreArray())).size();
        double genreUnion = (double) CollectionUtils.union(Arrays.asList(m1.getGenreArray()), Arrays.asList(m2.getGenreArray())).size();
        similarity += genreWeight * (genreIntersection / genreUnion);

        //If movies are around the same year of publication, they are more similar.
        try {
            int yearDifference = Math.abs(Integer.parseInt(m1.getYear()) - Integer.parseInt(m2.getYear()));

            if (yearDifference > 500)
                throw new RuntimeException();

            double w = yearWeight / (.9 * (double) yearDifference + 1);
            //System.out.println("OMDBItemItemPredictionAlgorithm: yearDifference " + yearDifference + " gives " + w);
            similarity += w;
        } catch (Exception e) {
            similarity += 2.5; // nothing can be said about the similarity
        }


        //If movies have the same director, they are more similar.
        double directorIntersection = (double) CollectionUtils.intersection(Arrays.asList(m1.getDirectorArray()), Arrays.asList(m2.getDirectorArray())).size();
        double directorUnion = (double) CollectionUtils.union(Arrays.asList(m1.getDirectorArray()), Arrays.asList(m2.getDirectorArray())).size();
        similarity += directorWeight * (directorIntersection / directorUnion);

        //foreign films are less likely to be the same
        String m1lang = m1.getLanguage().split(", ")[0];
        String m2lang = m2.getLanguage().split(", ")[0];
        if (!m1lang.equals(m2lang) && !(m1lang.equals("N/A") || m2lang.equals("N/A"))) {
            similarity *= 0.8d;
        }

        return similarity;

    }

}
