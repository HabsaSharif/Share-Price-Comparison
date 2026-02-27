package com.sad.domain;

/* ComparisonResult represents the outcome of the compare use case by grouping the two retrieved price
 series into a single structured result. It allows the application layer to return meaningful comparison data
 to the presentation layer */

public class ComparisonResult {
    private final PriceSeries first;
    private final PriceSeries second;
    //The reference is final and so it's pointer cannot change, but the lists can still be altered.

    //Constructor to initialize a comparison containing two price series.
    public ComparisonResult(PriceSeries first, PriceSeries second) {
        this.first = first;
        this.second = second;
    }

    //Getters to access from outside of class.
    public PriceSeries getFirst() { return first; }
    public PriceSeries getSecond() { return second; }
}