package com.sad.UI.mvc;

import com.sad.domain.Portfolio;
import com.sad.domain.Ticker;
import com.sad.ports.IUserMgt;

public class PortfolioController {
    private final IUserMgt userMgt;
    private final SessionModel sessionModel;

    public PortfolioController(IUserMgt userMgt, SessionModel sessionModel) {
        this.userMgt = userMgt;
        this.sessionModel = sessionModel;
    }

    public Portfolio getCurrentPortfolio() {
        ensureLoggedIn();
        return userMgt.getCurrentUserPortfolio();
    }

    public void addTicker(String symbol) {
        addTicker(new Ticker(symbol));
    }

    public void addTicker(Ticker ticker) {
        ensureLoggedIn();
        userMgt.addTickerToCurrentPortfolio(ticker);
    }

    private void ensureLoggedIn() {
        if (!sessionModel.isLoggedIn()) {
            throw new IllegalStateException("Please login before using portfolio features.");
        }
    }
}
