package com.sad.domain;

import java.util.List;

/* ComparisonResult represents the outcome of the compare use case by grouping the two retrieved price
 series into a single structured result. It allows the application layer to return meaningful comparison data
 to the presentation layer */

public class ComparisonResult {
    private final List<PriceSeries> seriesList;
    // less rigid to reflect BTM

    //Constructor to initialize a comparison containing two price series.
    public ComparisonResult(List<PriceSeries> seriesList) {
        if (seriesList == null || seriesList.isEmpty() || seriesList.size() > 2) {
            throw new IllegalArgumentException("ComparisonResult must contain one or two price series.");
        }
        if (seriesList.size() == 2 &&
                seriesList.get(0).getTicker().equals(seriesList.get(1).getTicker())) {
            throw new IllegalArgumentException("Cannot compare the same ticker.");   //the redundancy
        }

        this.seriesList = seriesList;
    }

    public List<PriceSeries> getSeriesList() {
        return List.copyOf(seriesList);     //copy to be safe
    }

}

//note comp result is a application boundary object, sitting in between comp service and the ui
//big picture: User input
//→ ComparisonController
//→ ComparisonService
//→ ComparisonResult
//→ Renderer