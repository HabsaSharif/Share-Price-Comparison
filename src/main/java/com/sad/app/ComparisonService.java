package com.sad.app;

import com.sad.domain.ComparisonResult;
import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IComparisonMgt;
import com.sad.ports.IPriceMgt;

import java.util.ArrayList;
import java.util.List;

public class ComparisonService implements IComparisonMgt {

    private final IPriceMgt priceMgt;

    public ComparisonService(IPriceMgt priceMgt) {
        this.priceMgt = priceMgt;
    }

    @Override
    public ComparisonResult createComparisonResult(List<Ticker> tickers, DateRange dateRange) {
        if (tickers == null || tickers.isEmpty() || tickers.size() > 2) {
            throw new IllegalArgumentException("One or two tickers are required.");
        }

        List<PriceSeries> seriesList = new ArrayList<>();
        for (Ticker ticker : tickers) {
            seriesList.add(priceMgt.getPriceSeries(ticker, dateRange));
        }

        return new ComparisonResult(seriesList);
    }
}