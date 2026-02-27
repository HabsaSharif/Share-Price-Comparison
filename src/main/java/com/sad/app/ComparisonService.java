package com.sad.app;

import com.sad.domain.ComparisonResult;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IComparisonService;
import com.sad.ports.IPriceRepository;

public class ComparisonService implements IComparisonService {

    private final IPriceRepository repository;
    /* The service depends on the repository abstraction so it can obtain price data without knowing how or where it is retrieved. */

    //Constructor to initialize a Comparison Service object with any fitting repository
    public ComparisonService(IPriceRepository repository) {
        this.repository = repository;
    }

    //Implements the use case defined by the input port.
    @Override

    //Method will return a Comparison Result object, and takes two variables of the Ticker type as parameters
    public ComparisonResult compare(Ticker ticker1, Ticker ticker2) {

        //Temporary message in this skeleton implementation to show that the comparison is beginning to take place.
        System.out.println("\n[Comparison Service] Preparing comparison data for: "
                + ticker1.getSymbol() + " and " + ticker2.getSymbol());

        // Requests price data for each ticker from the repository.
        PriceSeries a = repository.loadPrices(ticker1);
        PriceSeries b = repository.loadPrices(ticker2);

        //Returns a ComparisonResult containing the two retrieved price series.
        return new ComparisonResult(a, b);
    }
}