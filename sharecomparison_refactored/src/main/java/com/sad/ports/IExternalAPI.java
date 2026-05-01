package com.sad.ports;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;

public interface IExternalAPI {
    PriceSeries requestPrices(Ticker ticker, DateRange dateRange);
}