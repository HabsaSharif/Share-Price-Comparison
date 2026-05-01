package com.sad.app;

import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IPriceMgt;
import com.sad.ports.IPriceRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PriceManagementService implements IPriceMgt {

    private final IPriceRepository repository;

    public PriceManagementService(IPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public PriceSeries getPriceSeries(Ticker ticker, DateRange dateRange) {
        return repository.loadPrices(ticker, dateRange);
    }

    @Override
    public void storePriceSeries(PriceSeries priceSeries) {
        repository.storePrices(priceSeries);
    }

    /*@Override
    public void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates must not be null.");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }

        if (ChronoUnit.DAYS.between(startDate, endDate) > 366 * 2L) {
            throw new IllegalArgumentException("Date range must not exceed two years.");
        }
    } */
}