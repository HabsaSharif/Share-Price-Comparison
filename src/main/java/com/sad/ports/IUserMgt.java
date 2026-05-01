package com.sad.ports;

import com.sad.domain.Portfolio;
import com.sad.domain.Ticker;
import com.sad.domain.UserAccount;

public interface IUserMgt extends IAuthService, IPortfolioService {
    UserAccount getCurrentUser();
    Portfolio getCurrentUserPortfolio();
    void addTickerToCurrentPortfolio(Ticker ticker);
}
