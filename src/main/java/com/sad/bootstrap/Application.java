package com.sad.bootstrap;

import com.sad.UI.ConsolePriceGraphRenderer;
import com.sad.UI.UserInterface;
import com.sad.adapters.external.ExternalAPI;
import com.sad.adapters.market.MarketDataProvider;
import com.sad.adapters.repository.PriceRepository;
import com.sad.adapters.store.LocalPriceStore;
import com.sad.app.ComparisonService;
import com.sad.app.PriceManagementService;
import com.sad.app.SharePriceSystemService;
import com.sad.ports.*;

import java.time.LocalDate;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        IExternalAPI externalAPI = new ExternalAPI();
        IMarketDataProvider marketProvider = new MarketDataProvider(externalAPI);
        ILocalPriceStore localStore = new LocalPriceStore();

        IPriceRepository repository = new PriceRepository(localStore, marketProvider);
        IPriceMgt priceMgt = new PriceManagementService(repository);
        IComparisonMgt comparisonMgt = new ComparisonService(priceMgt);
        IViewPriceGraph graphRenderer = new ConsolePriceGraphRenderer();

        SharePriceSystemService systemService =
                new SharePriceSystemService(priceMgt, comparisonMgt, graphRenderer);

        UserInterface ui = new UserInterface(systemService, systemService);

        ui.compareTickers(
                List.of("AMZN", "TSLA"),
                LocalDate.now().minusDays(2),
                LocalDate.now()
        );

        System.out.println("\nNow testing to see if it pulls from local store the second time around:");

        ui.compareTickers(
                List.of("AMZN", "TSLA"),
                LocalDate.now().minusDays(2),
                LocalDate.now()
        );
    }
}