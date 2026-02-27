package com.sad.bootstrap;

import com.sad.adapters.external.ExternalAPI;
import com.sad.adapters.market.MarketDataProvider;
import com.sad.adapters.repository.PriceRepository;
import com.sad.adapters.store.LocalPriceStore;
import com.sad.app.ComparisonService;
import com.sad.ports.IComparisonService;
import com.sad.ports.IPriceRepository;
import com.sad.UI.UserInterface;

public class Application {

    public static void main(String[] args) {

        var externalAPI = new ExternalAPI();
        var marketProvider = new MarketDataProvider(externalAPI);
        var storageProvider = new LocalPriceStore();

        IPriceRepository repo = new PriceRepository(storageProvider, marketProvider);
        IComparisonService service = new ComparisonService(repo);

        UserInterface ui = new UserInterface(service);
        ui.compareTickers("AAPL", "MSFT");
        ui.compareTickers("AAPL", "MSFT");
    }
}

