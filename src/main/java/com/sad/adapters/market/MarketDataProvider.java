package com.sad.adapters.market;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IExternalAPI;
import com.sad.ports.IMarketDataProvider;

public class MarketDataProvider implements IMarketDataProvider {

    private final IExternalAPI externalAPI;

    public MarketDataProvider(IExternalAPI externalAPI) {
        this.externalAPI = externalAPI;
    }

    @Override
    public PriceSeries fetch(Ticker ticker, DateRange dateRange) {
        System.out.println("\n[MarketDataProvider] Requesting data from external API for " + ticker.getSymbol());
        return externalAPI.requestPrices(ticker, dateRange);
    }
}