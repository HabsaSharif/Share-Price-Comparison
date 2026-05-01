package com.sad.UI.mvc;

import com.sad.domain.ComparisonResult;
import com.sad.domain.DateRange;
import com.sad.domain.Ticker;
import com.sad.ports.ICompareSharePrices;

import java.util.List;

public class ComparisonController {
    private final ICompareSharePrices comparisonService;
    private final ComparisonModel comparisonModel;

    public ComparisonController(ICompareSharePrices comparisonService, ComparisonModel comparisonModel) {
        this.comparisonService = comparisonService;
        this.comparisonModel = comparisonModel;
    }

    public ComparisonResult compare(List<String> symbols, DateRange dateRange) {
        List<Ticker> tickers = symbols.stream()
                .filter(symbol -> symbol != null && !symbol.isBlank())
                .map(Ticker::new)
                .toList();
        ComparisonResult result = comparisonService.compareSharePrices(tickers, dateRange);
        comparisonModel.setLatestResult(result);
        return result;
    }
}
