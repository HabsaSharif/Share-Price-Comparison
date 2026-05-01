package com.sad.domain;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

// Represents a user's saved collection of unique ticker symbols.
public class Portfolio {
    private final String username;

    // LinkedHashSet prevents duplicates while keeping insertion order.
    private final Set<Ticker> tickers;

    public Portfolio(String username, Set<Ticker> tickers) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must not be empty.");
        }
        if (tickers == null) {
            throw new IllegalArgumentException("Tickers must not be null.");
        }

        this.username = username.trim();
        this.tickers = new LinkedHashSet<>(tickers);
    }

    public String getUsername() {
        return username;
    }

    // Read-only view so outside code cannot directly modify the portfolio.
    public Set<Ticker> getTickers() {
        return Collections.unmodifiableSet(tickers);
    }

    public boolean addTicker(Ticker ticker) {
        if (ticker == null) {
            throw new IllegalArgumentException("Ticker must not be null.");
        }

        // Set.add returns true if the ticker was added,
        // false if it was already present.
        return tickers.add(ticker);
    }
}