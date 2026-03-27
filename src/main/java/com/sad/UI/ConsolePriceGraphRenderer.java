package com.sad.UI;

import com.sad.domain.ComparisonResult;
import com.sad.domain.PricePoint;
import com.sad.domain.PriceSeries;
import com.sad.ports.IViewPriceGraph;

public class ConsolePriceGraphRenderer implements IViewPriceGraph {

    @Override
    public void viewPriceGraph(ComparisonResult result) {
        System.out.println("\n[ViewPriceGraph] Rendering result...\n");

        for (PriceSeries series : result.getSeriesList()) {
            renderSeries(series);
        }
    }

    private void renderSeries(PriceSeries series) {
        System.out.println("Ticker: " + series.getTicker().getSymbol());
        System.out.println("Range: " + series.getDateRange().getStartDate()
                + " to " + series.getDateRange().getEndDate());

        for (PricePoint point : series.getPrices()) {
            System.out.println("  " + point.getDate() + " -> " + point.getPrice());
        }

        System.out.println();
    }
}