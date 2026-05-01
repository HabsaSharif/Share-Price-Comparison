package com.sad.domain;

import java.util.Locale;
import java.util.Objects;

//domain level object; ticker. shared business concept depended on by outer layers like comparison service.

public class Ticker {
    private final String symbol;

    public Ticker(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Ticker symbol must not be empty.");
        }
        this.symbol = symbol.trim().toUpperCase(Locale.ROOT);
        //locale.ROOT to safely convert to uppercase regardless of language. trim whitespace.
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    //checking for redundancy, if another object is logically the same with boolean
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticker ticker)) return false;
        //checking if object being compared is even ticker, if not return false
        return symbol.equals(ticker.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
    //LEARNT: you have to include this whenever you override equals, because hashcode has to match if equals. is true.

    @Override
    public String toString() {
        return symbol;
    }
    //for user output
}


