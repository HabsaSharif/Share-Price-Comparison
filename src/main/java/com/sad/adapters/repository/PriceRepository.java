package com.sad.adapters.repository;

import com.sad.domain.*;
import com.sad.ports.*;

public class PriceRepository implements IPriceRepository {

    private final ILocalPriceStore localStore;
    /* The repository depends on the storage abstraction to read and cache price data without knowing the storage technology. */

    private final IMarketDataProvider marketProvider;
      /* The repository depends on IMarketDataProvider abstraction so it can obtain price data from a provider without knowing
    what specific provider type it is pulled from */

    //Constructor injects the required data sources the repository will coordinate between
    public PriceRepository(ILocalPriceStore localStore, IMarketDataProvider marketProvider) {
        this.localStore = localStore;
        this.marketProvider = marketProvider;
    }

    //Implements the data access behaviour required by the application output port
    @Override

    //Method will return a PriceSeries object and takes a Ticker object as a parameter
    public PriceSeries loadPrices(Ticker ticker) {

        //Requests cached data from the storage abstraction
        //It is then saved as a PriceSeries object to the var 'local'.
        PriceSeries local = localStore.read(ticker);

        //If cached data exists (local != null)
        if (local != null) {
            System.out.println("\nSuccessfully retrieved " + ticker.getSymbol() + " price series from local storage.");
            return local;

        }

        /* Otherwise, if the required data is not available locally, it will call on the concrete implementation of the IMarketProvider
          via its 'fetch' method, to retrieve the required price history, which is saved as a Price Series object */
        PriceSeries fetched = marketProvider.fetch(ticker);

        //The fetched data is cached locally for future requests.
        localStore.write(fetched);

        //Then we return the Price Series object to the ComparisonService layer.
        return fetched;
    }
}
