package com.sad.ports;
import com.sad.domain.*;

public interface IMarketDataProvider {
    PriceSeries fetch(Ticker ticker);
}

/* IMarketDataProvider is an application output port.
It defines the capability required by the application layer to obtain market price data from an external source
while hiding infrastructure details such as the specific API used.
PriceRepository depends only on this abstraction, and the concrete implementation is supplied during application wiring,
enforcing dependency inversion between the application and infrastructure layers.
 */
