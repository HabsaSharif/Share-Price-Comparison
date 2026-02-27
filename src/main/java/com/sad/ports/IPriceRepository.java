package com.sad.ports;
import com.sad.domain.*;

//what and why
public interface IPriceRepository {
    PriceSeries loadPrices(Ticker ticker);
}

/* IPriceRepository is an application output port.
It specifies the data access capability required by the application layer (loading price history)
while hiding infrastructure details such as caching or external APIs.
ComparisonService depends only on this contract, and the concrete repository implementation
is provided during application wiring, enforcing dependency inversion between the application and infrastructure layers.
 */