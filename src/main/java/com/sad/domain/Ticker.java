package com.sad.domain;

/* Ticker class will hold the ticker symbol (abbreviations to identify stock/shares)
*  It is included in the domain package because it represents a real concept (stock identifier)
* */

public class Ticker {
    private final String symbol;
    //Initialising symbol variable as a string. It will not be changed after creation thus its final.
    //Private to ensure encapsulation and that it's only accessible through getters.

    //Constructor to initialise ticker object.
    public Ticker(String symbol) {
        this.symbol = symbol;
    }

    //Getter to access the symbol of a ticker object from outside its class.
    public String getSymbol() {
        return symbol;
    }
}
