package com.sad.adapters.store;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.ILocalPriceStore;

import java.util.HashMap;
import java.util.Map;

public class LocalPriceStore implements ILocalPriceStore {

    private final Map<String, PriceSeries> storage = new HashMap<>();

    private String key(Ticker ticker, DateRange range) {
        return ticker.getSymbol() + "|" + range.getStartDate() + "|" + range.getEndDate();
    }

    @Override
    public PriceSeries read(Ticker ticker, DateRange dateRange) {
        return storage.get(key(ticker, dateRange));
    }

    @Override
    public void write(PriceSeries series) {
        storage.put(
                key(series.getTicker(), series.getDateRange()),
                series
        );
        System.out.println("\nSuccessfully saved " + series.getTicker().getSymbol() + " price series to local storage.");
    }
}