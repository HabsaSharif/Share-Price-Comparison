package com.sad.usermgt;

import com.sad.domain.Portfolio;
import com.sad.domain.Ticker;
import com.sad.domain.UserAccount;
import com.sad.ports.IAuthService;
import com.sad.ports.IPortfolioService;
import com.sad.ports.IUserMgt;

public class UserMgtSystem implements IUserMgt {
    private final IAuthService authService;
    private final IPortfolioService portfolioService;
    private UserAccount currentUser;

    public UserMgtSystem(IAuthService authService, IPortfolioService portfolioService) {
        this.authService = authService;
        this.portfolioService = portfolioService;
    }

    @Override
    public UserAccount login(String username, String password) {
        currentUser = authService.login(username, password);
        return currentUser;
    }

    @Override
    public void logout() {
        authService.logout();
        currentUser = null;
    }

    @Override
    public UserAccount getCurrentUser() {
        return currentUser;
    }

    @Override
    public Portfolio getCurrentUserPortfolio() {
        ensureLoggedIn();
        return portfolioService.getPortfolioForUser(currentUser.getUsername());
    }

    @Override
    public void addTickerToCurrentPortfolio(Ticker ticker) {
        ensureLoggedIn();
        portfolioService.addTicker(currentUser.getUsername(), ticker);
    }

    @Override
    public Portfolio getPortfolioForUser(String username) {
        return portfolioService.getPortfolioForUser(username);
    }

    @Override
    public void addTicker(String username, Ticker ticker) {
        portfolioService.addTicker(username, ticker);
    }

    private void ensureLoggedIn() {
        if (currentUser == null) {
            throw new IllegalStateException("User must be logged in before accessing portfolio features.");
        }
    }
}
