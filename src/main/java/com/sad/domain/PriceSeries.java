package com.sad.domain;

import java.util.List; //Importing java utility library to access lists.

public class PriceSeries {
    private final Ticker ticker;

    /* Passing a ticker symbol as an attribute is important so that the object is self-describing.
       The data flows through multiple layers, so instead of just getting a list of price points that could be mixed up,
       we will always know what ticker symbol goes with it.

       It is included in the domain package because the Price Series represents a real business concept.
       */

    private final List<PricePoint> prices;
    /* List to store multiple price points into one price series object. Reference is fixed with final, but contents of list
    are still liable to change */

    //Constructor binds ticker to its data, forming one domain object.
    public PriceSeries(Ticker ticker, List<PricePoint> prices) {
        this.ticker = ticker;
        this.prices = prices;
    }

    //Getters to access from outside of class. Encapsulation.
    public Ticker getTicker() { return ticker; }
    public List<PricePoint> getPrices() { return prices; }
}

