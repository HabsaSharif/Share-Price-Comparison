package com.sad.domain;

import java.time.LocalDate;

//cause date range is also a real business concept, it cant exceed  2 years

public class DateRange {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public DateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates must not be empty.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }

        if (startDate.plusYears(2).isBefore(endDate)) {
            throw new IllegalArgumentException("Date range cannot exceed 2 years.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
}
