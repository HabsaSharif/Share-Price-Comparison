package com.sad.ports;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;

public interface IMarketDataProvider {
    PriceSeries fetch(Ticker ticker, DateRange dateRange);
}