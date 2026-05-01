package com.sad.ports;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;

public interface ILocalPriceStore {
    PriceSeries read(Ticker ticker, DateRange dateRange);
    void write(PriceSeries series);
}