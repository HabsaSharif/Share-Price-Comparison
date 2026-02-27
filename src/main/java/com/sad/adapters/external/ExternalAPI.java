package com.sad.adapters.external;
import com.sad.domain.PricePoint;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IExternalAPI;
import java.time.LocalDate;
import java.util.List;

//Concrete implementation of the IExternalAPI interface
public class ExternalAPI implements IExternalAPI {

    //Simulates an external API response for testing purposes.
    @Override

    public PriceSeries requestPrices(Ticker ticker) {
        //Temporary message and fake data, where proper API logic will be implemented in later sprints.
        System.out.println("\n[ExternalAPI] Successfully retrieved data for " + ticker.getSymbol());

        //Creates mock historical price data to construct a PriceSeries response.
        List<PricePoint> data = List.of(
                //Uses LocalDate.now().minusDays(n) to simulate historical daily prices.
                new PricePoint(LocalDate.now().minusDays(2), 100),
                new PricePoint(LocalDate.now().minusDays(1), 105),
                new PricePoint(LocalDate.now(), 110)
        );

        //then we return the new PriceSeries object to the MarketDataProvider
        return new PriceSeries(ticker, data);
    }
}
