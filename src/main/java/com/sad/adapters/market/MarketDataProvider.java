package com.sad.adapters.market;

import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IExternalAPI;
import com.sad.ports.IMarketDataProvider;

//Concrete implementation of IMarketDataProvider interface
public class MarketDataProvider implements IMarketDataProvider {

    //Object of IExternalAPI type as attribute
    private final IExternalAPI externalAPI;

    //Constructor injects an external API abstraction the provider can delegate requests to.
    public MarketDataProvider(IExternalAPI externalAPI) {
        this.externalAPI = externalAPI;
    }

    //Adapts the application’s market data request into an external API call.
    @Override

    //Accepts a ticker and returns its price series.
    public PriceSeries fetch(Ticker ticker) {
        //Temporary message to prove communication between MarketDataProvider and placeholder External API
        System.out.println("\n[MarketDataProvider] Requesting data from external API for " + ticker.getSymbol());
        //Delegates the request to the external API abstraction.
        return externalAPI.requestPrices(ticker);
    }
}