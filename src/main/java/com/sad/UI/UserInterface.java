package com.sad.UI;

import com.sad.domain.ComparisonResult;
import com.sad.domain.DateRange;
import com.sad.domain.Ticker;
import com.sad.ports.ICompareSharePrices;
import com.sad.ports.IViewPriceGraph;

import java.time.LocalDate;
import java.util.List;

public class UserInterface {

    private final ICompareSharePrices comparisonService;
    private final IViewPriceGraph graphViewer;

    public UserInterface(ICompareSharePrices comparisonService, IViewPriceGraph graphViewer) {
        this.comparisonService = comparisonService;
        this.graphViewer = graphViewer;
    }

    public void compareTickers(List<String> symbols, LocalDate startDate, LocalDate endDate) {
        List<Ticker> tickers = symbols.stream().map(Ticker::new).toList();
        DateRange dateRange = new DateRange(startDate, endDate);

        System.out.println("\n[User Interface] Comparing " + symbols);

        ComparisonResult result = comparisonService.compareSharePrices(tickers, dateRange);
        graphViewer.viewPriceGraph(result);
    }
}