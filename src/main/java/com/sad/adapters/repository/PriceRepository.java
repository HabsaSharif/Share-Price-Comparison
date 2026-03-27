package com.sad.adapters.repository;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.ILocalPriceStore;
import com.sad.ports.IMarketDataProvider;
import com.sad.ports.IPriceRepository;

public class PriceRepository implements IPriceRepository {

    private final ILocalPriceStore localStore;
    private final IMarketDataProvider marketProvider;

    public PriceRepository(ILocalPriceStore localStore, IMarketDataProvider marketProvider) {
        this.localStore = localStore;
        this.marketProvider = marketProvider;
    }

    @Override
    public PriceSeries loadPrices(Ticker ticker, DateRange dateRange) {
        PriceSeries local = localStore.read(ticker, dateRange);

        if (local != null) {
            System.out.println("\nSuccessfully retrieved " + ticker.getSymbol() + " price series from local storage.");
            return local;
        }

        PriceSeries fetched = marketProvider.fetch(ticker, dateRange);
        localStore.write(fetched);
        return fetched;
    }

    @Override
    public void storePrices(PriceSeries series) {
        localStore.write(series);
    }
}
