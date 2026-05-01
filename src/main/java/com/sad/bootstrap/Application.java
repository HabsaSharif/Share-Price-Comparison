package com.sad.bootstrap;

import com.sad.UI.TerminalUI;
import com.sad.UI.mvc.*;
import com.sad.adapters.external.ExternalMarketAPIAdapter;
import com.sad.adapters.repository.PriceRepository;
import com.sad.adapters.store.LocalPriceStore;
import com.sad.adapters.store.PortfolioDataStore;
import com.sad.adapters.store.UserDataStore;
import com.sad.app.ComparisonService;
import com.sad.app.PriceManagementService;
import com.sad.app.SharePriceSystemService;
import com.sad.ports.*;
import com.sad.usermgt.AccountService;
import com.sad.usermgt.AuthService;
import com.sad.usermgt.PortfolioService;
import com.sad.usermgt.UserMgtSystem;

public class Application {

    public static void main(String[] args) {
        // DataMgt compound component
        IUserDataStore userDataStore = new UserDataStore();
        IPortfolioDataStore portfolioDataStore = new PortfolioDataStore();
        ILocalPriceStore localPriceStore = new LocalPriceStore();

        // UserMgt compound component
        IAccountService accountService = new AccountService(userDataStore);
        IAuthService authService = new AuthService(accountService);
        IPortfolioService portfolioService = new PortfolioService(portfolioDataStore);
        IUserMgt userMgt = new UserMgtSystem(authService, portfolioService);

        // PriceMgt compound component: repository checks local store first, then adapter/API if missing
        IExternalAPI externalMarketAPIAdapter = new ExternalMarketAPIAdapter();
        IPriceRepository priceRepository = new PriceRepository(localPriceStore, externalMarketAPIAdapter);
        IPriceMgt priceMgt = new PriceManagementService(priceRepository);

        // ComparisonMgt compound component
        IComparisonMgt comparisonMgt = new ComparisonService(priceMgt);
        SharePriceSystemService sharePriceSystemService = new SharePriceSystemService(priceMgt, comparisonMgt, result -> { });

        // UI compound component using MVC
        SessionModel sessionModel = new SessionModel();
        AuthController authController = new AuthController(userMgt, sessionModel);
        PortfolioController portfolioController = new PortfolioController(userMgt, sessionModel);
        ComparisonModel comparisonModel = new ComparisonModel();
        ComparisonController comparisonController = new ComparisonController(sharePriceSystemService, comparisonModel);

        LoginView loginView = new LoginView(authController, sessionModel);
        PortfolioView portfolioView = new PortfolioView(portfolioController, sessionModel);
        ComparisonGraphView graphView = new ComparisonGraphView(comparisonController, comparisonModel);

        TerminalUI terminalUI = new TerminalUI(
                loginView,
                authController,
                portfolioView,
                portfolioController,
                graphView,
                sessionModel
        );

        terminalUI.run();
    }
}
