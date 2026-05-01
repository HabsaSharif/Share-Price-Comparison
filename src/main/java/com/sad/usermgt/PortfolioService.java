package com.sad.usermgt;

import com.sad.domain.Portfolio;
import com.sad.domain.Ticker;
import com.sad.ports.IPortfolioDataStore;
import com.sad.ports.IPortfolioService;

public class PortfolioService implements IPortfolioService {
    private final IPortfolioDataStore portfolioDataStore;

    public PortfolioService(IPortfolioDataStore portfolioDataStore) {
        this.portfolioDataStore = portfolioDataStore;
    }

    @Override
    public Portfolio getPortfolioForUser(String username) {
        return portfolioDataStore.findByUsername(username);
    }

    @Override
    public void addTicker(String username, Ticker ticker) {
        portfolioDataStore.addTicker(username, ticker);
    }
}
