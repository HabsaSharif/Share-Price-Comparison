package com.sad.ports;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;

import java.time.LocalDate;

public interface IPriceMgt {
    PriceSeries getPriceSeries(Ticker ticker, DateRange dateRange);
    void storePriceSeries(PriceSeries priceSeries);
    void validateDateRange(LocalDate startDate, LocalDate endDate);
}