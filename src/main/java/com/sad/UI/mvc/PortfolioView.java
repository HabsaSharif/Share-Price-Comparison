package com.sad.UI.mvc;

import com.sad.domain.Portfolio;
import com.sad.domain.Ticker;

public class PortfolioView implements SessionObserver {
    private final PortfolioController portfolioController;
    private boolean enabled;

    public PortfolioView(PortfolioController portfolioController, SessionModel sessionModel) {
        this.portfolioController = portfolioController;
        sessionModel.subscribe(this);
    }

    public void addTicker(String symbol) {
        if (!enabled) {
            System.out.println("Please login before using portfolio features.");
            return;
        }
        portfolioController.addTicker(new Ticker(symbol));
    }

    public Portfolio getPortfolio() {
        if (!enabled) {
            throw new IllegalStateException("Please login before using portfolio features.");
        }
        return portfolioController.getCurrentPortfolio();
    }

    public void showPortfolio() {
        Portfolio portfolio = getPortfolio();
        System.out.println("\nPortfolio for " + portfolio.getUsername() + ":");
        if (portfolio.getTickers().isEmpty()) {
            System.out.println("  No tickers saved yet.");
            return;
        }
        portfolio.getTickers().forEach(ticker -> System.out.println("  - " + ticker.getSymbol()));
    }

    @Override
    public void onSessionChanged(SessionModel sessionModel) {
        enabled = sessionModel.isLoggedIn();
    }
}
