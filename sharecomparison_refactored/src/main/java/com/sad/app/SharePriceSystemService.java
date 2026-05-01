package com.sad.app;

import com.sad.domain.ComparisonResult;
import com.sad.domain.DateRange;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.ICompareSharePrices;
import com.sad.ports.IComparisonMgt;
import com.sad.ports.IPriceMgt;
import com.sad.ports.IRequestSharePriceData;
import com.sad.ports.IViewPriceGraph;

import java.util.List;

public class SharePriceSystemService implements IRequestSharePriceData, ICompareSharePrices, IViewPriceGraph {

    private final IPriceMgt priceMgt;
    private final IComparisonMgt comparisonMgt;
    private final IViewPriceGraph graphViewer;

    public SharePriceSystemService(IPriceMgt priceMgt, IComparisonMgt comparisonMgt, IViewPriceGraph graphViewer) {
        this.priceMgt = priceMgt;
        this.comparisonMgt = comparisonMgt;
        this.graphViewer = graphViewer;
    }

    @Override
    public PriceSeries requestSharePriceData(Ticker ticker, DateRange dateRange) {
        //priceMgt.validateDateRange(dateRange.getStartDate(), dateRange.getEndDate());
        return priceMgt.getPriceSeries(ticker, dateRange);
    }

    @Override
    public ComparisonResult compareSharePrices(List<Ticker> tickers, DateRange dateRange) {
        //priceMgt.validateDateRange(dateRange.getStartDate(), dateRange.getEndDate());
        return comparisonMgt.createComparisonResult(tickers, dateRange);
    }

    @Override
    public void viewPriceGraph(ComparisonResult result) {
        graphViewer.viewPriceGraph(result);
    }
}
