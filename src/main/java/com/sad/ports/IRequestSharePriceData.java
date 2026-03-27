package com.sad.ports;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;

public interface IRequestSharePriceData {
    PriceSeries requestSharePriceData(Ticker ticker, DateRange dateRange);
}