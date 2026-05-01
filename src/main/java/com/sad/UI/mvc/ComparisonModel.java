package com.sad.UI.mvc;

import com.sad.domain.ComparisonResult;

import java.util.ArrayList;
import java.util.List;

public class ComparisonModel {
    private final List<ComparisonObserver> observers = new ArrayList<>();
    private ComparisonResult latestResult;

    public void subscribe(ComparisonObserver observer) {
        observers.add(observer);
    }

    public ComparisonResult getLatestResult() {
        return latestResult;
    }

    public void setLatestResult(ComparisonResult latestResult) {
        this.latestResult = latestResult;
        for (ComparisonObserver observer : observers) {
            observer.onComparisonChanged(this);
        }
    }
}
