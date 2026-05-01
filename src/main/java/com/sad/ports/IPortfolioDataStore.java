package com.sad.ports;

import com.sad.domain.Portfolio;
import com.sad.domain.Ticker;

public interface IPortfolioDataStore {
    Portfolio findByUsername(String username);
    void addTicker(String username, Ticker ticker);
}
