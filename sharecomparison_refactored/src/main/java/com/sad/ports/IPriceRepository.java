package com.sad.ports;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;

public interface IPriceRepository {
    PriceSeries loadPrices(Ticker ticker, DateRange dateRange);
    void storePrices(PriceSeries series);
}