package com.sad.UI.mvc;

import com.sad.UI.ConsolePriceGraphRenderer;
import com.sad.domain.ComparisonResult;
import com.sad.domain.DateRange;

import java.time.LocalDate;
import java.util.List;

public class ComparisonGraphView implements ComparisonObserver {
    private final ComparisonController controller;
    private final ConsolePriceGraphRenderer renderer = new ConsolePriceGraphRenderer();

    public ComparisonGraphView(ComparisonController controller, ComparisonModel comparisonModel) {
        this.controller = controller;
        comparisonModel.subscribe(this);
    }

    public ComparisonResult requestComparison(List<String> symbols, LocalDate startDate, LocalDate endDate) {
        return controller.compare(symbols, new DateRange(startDate, endDate));
    }

    @Override
    public void onComparisonChanged(ComparisonModel comparisonModel) {
        renderer.viewPriceGraph(comparisonModel.getLatestResult());
    }
}
