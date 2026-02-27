package com.sad.adapters.store;
import com.sad.domain.*;
import com.sad.ports.*;

/* LocalPriceStore is an infrastructure adapter that provides an in-memory cache implementation of the storage output port. */

import java.util.HashMap;   //Importing hash maps for key-data pairing.
import java.util.Map;

//Concrete implementation of ILocalPriceStore interface
public class LocalPriceStore implements ILocalPriceStore {

    //Immediately initialises a new HashMap as an attribute. Of the type String:Key, PriceSeries:data.
    private final Map<String, PriceSeries> storage = new HashMap<>();

    //Implements the data access behaviour required by the application output port
    @Override

    //Method will return the cached PriceSeries for the given ticker, or null if not present.
    public PriceSeries read(Ticker ticker) {
        /*If there exists a price series for the ticker passed as an argument, it will retrieve it from the storage
        hash map based on its ticker symbol key.  */
        return storage.get(ticker.getSymbol());
    }

    @Override

    //Method will write the PriceSeries object passed as an argument into the storage hash map.
    public void write(PriceSeries series) {
        System.out.println("\nSuccessfully saved " + series.getTicker().getSymbol() + " price series to local storage.");
        storage.put(series.getTicker().getSymbol(), series);
    }
}
