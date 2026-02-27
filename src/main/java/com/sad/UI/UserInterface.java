package com.sad.UI;
import com.sad.domain.ComparisonResult;
import com.sad.domain.PricePoint;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IComparisonService;

public class UserInterface {

    //IComparisonService type object assigned as attribute, so that we can place requests through its interface.
    private final IComparisonService comparisonService;

    //Constructor to inject IComparisonService object into UserInterface object
    public UserInterface(IComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    //Method to output the comparison, no calculations or data handling taking place.
    public void compareTickers(String t1, String t2) {

        //Temporary message to prove user request has gone through.
        System.out.println("\n[User Interface] Comparing " + t1 + " with " + t2);

        //Create variable to store the result of the comparison between user specified tickers, call on IComparisonService
        var result = comparisonService.compare(new Ticker(t1), new Ticker(t2));

        //Helper method to render results.
        renderComparison(result);

    }

    //Outputs the ComparisonResult for both tickers (aka the price series for each ticker)
    private void renderComparison(ComparisonResult result) {

        System.out.println("\nComparison of: "
                + result.getFirst().getTicker().getSymbol()
                + " vs "
                + result.getSecond().getTicker().getSymbol());

        //Helper method to display the contents of the price series for each ticker
        renderSeries(result.getFirst());
        renderSeries(result.getSecond());
    }

    private void renderSeries(PriceSeries series) {
        System.out.println("\n" + series.getTicker().getSymbol() + ":");

        for (PricePoint point : series.getPrices()) {
            System.out.println(point.getDate() + " -> " + point.getPrice());
        }
    }
}