package com.sad.adapters.repository;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IExternalAPI;
import com.sad.ports.ILocalPriceStore;
import com.sad.ports.IPriceRepository;

//abstracts where price data comes from

public class PriceRepository implements IPriceRepository {

    private final ILocalPriceStore localStore;
    private final IExternalAPI externalMarketAPIAdapter;

    public PriceRepository(ILocalPriceStore localStore, IExternalAPI externalMarketAPIAdapter) {
        this.localStore = localStore;
        this.externalMarketAPIAdapter = externalMarketAPIAdapter;
    }

    @Override
    public PriceSeries loadPrices(Ticker ticker, DateRange dateRange) {
        PriceSeries local = localStore.read(ticker, dateRange);

        if (local != null) {
            System.out.println("\n[PriceRepository] Retrieved " + ticker.getSymbol() + " from LocalPriceStore.");
            return local;
        }

        System.out.println("\n[PriceRepository] Local data missing. Fetching " + ticker.getSymbol() + " from external service.");
        PriceSeries fetched = externalMarketAPIAdapter.requestPrices(ticker, dateRange);
        localStore.write(fetched);
        return fetched;
    }

    @Override
    public void storePrices(PriceSeries series) {
        localStore.write(series);
    }
}
