package com.sad.ports;

import com.sad.domain.ComparisonResult;
import com.sad.domain.DateRange;
import com.sad.domain.Ticker;

import java.util.List;

public interface IComparisonMgt {
    ComparisonResult createComparisonResult(List<Ticker> tickers, DateRange dateRange);
}