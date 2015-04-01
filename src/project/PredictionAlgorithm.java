package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ti2736c.RatingList;

/**
 * Created by Dereck on 3/5/2015.
 */
public abstract class PredictionAlgorithm {
    String name;
    RatingLoopRunnable runnable;
    BeforeData beforeRunnable;

    public PredictionAlgorithm(String name) {
        this.name = name;
    }

    public RatingLoopRunnable getRunnable() {
        return runnable;
    }

    public void setRunnable(RatingLoopRunnable runnable) {
        this.runnable = runnable;
    }

    public BeforeData getBeforeRunnable() {
        return beforeRunnable;
    }

    public void setBeforeRunnable(BeforeData beforeRunnable) {
        this.beforeRunnable = beforeRunnable;
    }

    public abstract RatingList getPrediction(PredictionData data, RatingList ratingsToPredict);

    public String getName() {
        return name;
    }

    static Random random = new Random();

    <T> List<T> sample(List<T> list, int n) {
        if (list.size() < n)
            return list;
        List<T> newList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            newList.add(list.get(random.nextInt(list.size())));
        }
        return newList;
    }
}

