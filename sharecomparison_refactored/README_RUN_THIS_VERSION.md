# How to run this version

Open the project in IntelliJ and run:

`com.sad.bootstrap.Application`

The application starts in the terminal with a menu.

Preset logins:

- `alice` / `pass123`
- `bob` / `pass123`
- `charlie` / `pass123`

What works:

- Compare one ticker without login.
- Compare two tickers without login.
- Login.
- Portfolio features only work after login.
- Save a one-ticker comparison to portfolio when logged in.
- View saved portfolio tickers.
- View performance graph for a saved portfolio ticker.
- Local account, portfolio, and price data persist in the `data/` folder.
- PriceRepository checks local stored prices first.
- If local price data is missing, ExternalMarketAPIAdapter tries to fetch real historical CSV data from Stooq.
- If the external API is unavailable, generated fallback data keeps the demo working.
