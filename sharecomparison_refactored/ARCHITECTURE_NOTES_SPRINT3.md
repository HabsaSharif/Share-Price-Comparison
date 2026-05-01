# Sprint 3 Refactor Notes

This version keeps the original share-price comparison behaviour, but restructures the code to match the updated Sprint 3 architecture diagram.

## Main architecture mapping

### UI compound component using MVC
- `com.sad.UI.mvc.LoginView`
- `com.sad.UI.mvc.AuthController`
- `com.sad.UI.mvc.SessionModel`
- `com.sad.UI.mvc.PortfolioView`
- `com.sad.UI.mvc.PortfolioController`
- `com.sad.UI.mvc.ComparisonGraphView`
- `com.sad.UI.mvc.ComparisonController`
- `com.sad.UI.mvc.ComparisonModel`

The models are intentionally simple. `SessionModel` stores login/session state. `ComparisonModel` stores the latest comparison result. Views subscribe to models to simulate the MVC publish/subscribe update relationship from the lecture slides.

### UserMgt compound component
- `com.sad.usermgt.AuthService`
- `com.sad.usermgt.AccountService`
- `com.sad.usermgt.PortfolioService`
- `com.sad.usermgt.UserMgtSystem`

`UserMgtSystem` acts as the subsystem boundary/facade that the UI controllers use through `IUserMgt`.

### PriceMgt compound component
- `com.sad.app.PriceManagementService`
- `com.sad.adapters.repository.PriceRepository`

`PriceRepository` checks local storage first. If the price series is missing, it calls the external market API adapter and then stores the result locally.

### DataMgt compound component
- `com.sad.adapters.store.UserDataStore`
- `com.sad.adapters.store.PortfolioDataStore`
- `com.sad.adapters.store.LocalPriceStore`

These are simple in-memory stores for the prototype. They can later be replaced with JSON or SQLite implementations.

### Adapter + SOA
- `com.sad.adapters.external.ExternalMarketAPIAdapter`

This replaces the old extra `MarketDataProvider` layer. The adapter talks to the external market data boundary through `IExternalAPI`.

## Demo login

The prototype includes demo accounts:

- username: `student`, password: `password`
- username: `demo`, password: `demo`

Run `com.sad.bootstrap.Application` to see login, portfolio access, share comparison, external API adapter use, and local price cache reuse.
