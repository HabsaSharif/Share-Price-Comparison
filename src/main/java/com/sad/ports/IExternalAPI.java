package com.sad.ports;

import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;

public interface IExternalAPI {
    PriceSeries requestPrices(Ticker ticker);
}

/* IExternalAPI is a secondary adapter/gateway/ infrastructure boundary interface used by the market data adapter to communicate with external services.
It allows the MarketDataProvider to depend on an abstraction rather than a concrete external API implementation,
enabling different external services to be substituted without modifying the adapter.

Application
   ↓
MarketDataProvider (adapter)
   ↓
IExternalAPI
   ↓
ExternalAPI (real system)

 */