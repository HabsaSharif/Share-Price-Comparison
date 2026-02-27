package com.sad.ports;
import com.sad.domain.*;

public interface ILocalPriceStore {
    PriceSeries read(Ticker ticker);
    void write(PriceSeries series);
}

/* ILocalPriceStore is an application output port.
It defines the storage capability required by the application layer for caching price data,
 while hiding infrastructure details such as whether the data is stored in memory, files, or a database.
PriceRepository depends only on this abstraction, and the concrete implementation is provided during application wiring,
 enforcing dependency inversion between the application and infrastructure layers.
 */