package project;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

import ti2736c.Rating;
import ti2736c.RatingList;
import ti2736c.User;

/**
 * Created by Dereck on 07/04/2015.
 */
public class UserMeanPredictionAlgorithm extends PredictionAlgorithm {
    List<DescriptiveStatistics> userStatistics = new ArrayList<>();

    public UserMeanPredictionAlgorithm() {
        super("UserMeanPredictionAlgorithm");
        this.setBeforeRunnable(new BeforeData() {
            @Override
            public void run(int userSize, int movieSize, int ratingSize) {
                userStatistics = new ArrayList<>(userSize);
                for (int i = 0; i < userSize; i++) {
                    userStatistics.add(new DescriptiveStatistics());
                }
            }
        });
        this.setRunnable(new RatingLoopRunnable() {
            @Override
            public void run(Rating r) {
                userStatistics.get(r.getUser().getIndex()).addValue(r.getRating());
            }
        });
    }

    @Override
    public RatingList getPrediction(PredictionData data, RatingList ratingsToPredict) {
        for (int i = 0; i < ratingsToPredict.size(); i++) {
            Rating toPredict = ratingsToPredict.get(i);
            User toPredictUser = toPredict.getUser();
            int a = toPredictUser.getIndex();

            double aAverage = userStatistics.get(a).getMean();

            toPredict.setRating(aAverage);
        }
        return ratingsToPredict;
    }
}
