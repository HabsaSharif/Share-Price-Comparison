package com.sad.ports;

import com.sad.domain.ComparisonResult;
import com.sad.domain.Ticker;

public interface IComparisonService {
    ComparisonResult compare(Ticker ticker1, Ticker ticker2);
}

/* IComparisonService is an application input port.
It defines the system capability “compare two tickers” without exposing implementation details.
The UI depends only on this contract, while the concrete service implementation is injected during bootstrap,
enforcing dependency inversion between presentation and application layers.
 */