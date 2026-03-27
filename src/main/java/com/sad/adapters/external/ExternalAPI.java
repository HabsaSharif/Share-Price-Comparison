package com.sad.adapters.external;

import com.sad.domain.DateRange;
import com.sad.domain.PricePoint;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IExternalAPI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExternalAPI implements IExternalAPI {

    @Override
    public PriceSeries requestPrices(Ticker ticker, DateRange dateRange) {
        System.out.println("\n[ExternalAPI] Successfully retrieved data for " + ticker.getSymbol());

        List<PricePoint> data = new ArrayList<>();

        LocalDate current = dateRange.getStartDate();
        double price = 100.0;

        while (!current.isAfter(dateRange.getEndDate())) {
            data.add(new PricePoint(current, price));
            current = current.plusDays(1);
            price += 5.0;
        }

        return new PriceSeries(ticker, dateRange, data);
    }
}