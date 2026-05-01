package com.sad.ports;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;

import java.time.LocalDate;

//in hexagonal architecture, interfaces are 'ports', the boundary of a system

public interface IPriceMgt {
    PriceSeries getPriceSeries(Ticker ticker, DateRange dateRange);
    void storePriceSeries(PriceSeries priceSeries);
    //void validateDateRange(LocalDate startDate, LocalDate endDate);
}