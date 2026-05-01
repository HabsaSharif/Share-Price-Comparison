package com.sad.adapters.external;

import com.sad.domain.DateRange;
import com.sad.domain.PricePoint;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IExternalAPI;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// making sense of API in domain concepts
public class ExternalMarketAPIAdapter implements IExternalAPI {
    private final ExternalAPI externalAPI;

    public ExternalMarketAPIAdapter() {
        this(new ExternalAPI());
    }

    public ExternalMarketAPIAdapter(ExternalAPI externalAPI) {
        this.externalAPI = externalAPI;
    }

    @Override
    public PriceSeries requestPrices(Ticker ticker, DateRange dateRange) {
        System.out.println("[ExternalMarketAPIAdapter] Requesting " + ticker.getSymbol() + " from ExternalAPI.");

        try {
            String csv = externalAPI.fetchDailyPricesCsv(
                    ticker.getSymbol(),
                    dateRange.getStartDate(),
                    dateRange.getEndDate()
            );

            List<PricePoint> points = parseStooqCsv(csv);

            if (!points.isEmpty()) {
                return new PriceSeries(ticker, dateRange, points);
            }

            System.out.println("[ExternalMarketAPIAdapter] External API returned no usable rows. Using fallback data.");
        } catch (IOException | InterruptedException | RuntimeException ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[ExternalMarketAPIAdapter] External API unavailable or ticker unsupported. Using fallback data.");
        }

        return fallbackSeries(ticker, dateRange);
    }

    private List<PricePoint> parseStooqCsv(String csv) {
        List<PricePoint> points = new ArrayList<>();
        String[] lines = csv.split("\\R");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.isEmpty() || line.equalsIgnoreCase("No data")) {
                continue;
            }

            String[] parts = line.split(",");

            if (parts.length >= 5) {
                LocalDate date = LocalDate.parse(parts[0]);
                double close = Double.parseDouble(parts[4]);
                points.add(new PricePoint(date, close));
            }
        }

        return points;
    }

    private PriceSeries fallbackSeries(Ticker ticker, DateRange dateRange) {
        List<PricePoint> data = new ArrayList<>();
        LocalDate current = dateRange.getStartDate();
        double price = basePriceFor(ticker);
        int day = 0;

        while (!current.isAfter(dateRange.getEndDate())) {
            if (current.getDayOfWeek().getValue() <= 5) {
                double movement = Math.sin(day / 3.0) * 1.8
                        + (Math.abs(ticker.getSymbol().hashCode()) % 7 - 3) * 0.12;
                price = Math.max(1.0, price + movement);
                data.add(new PricePoint(current, Math.round(price * 100.0) / 100.0));
                day++;
            }
            current = current.plusDays(1);
        }

        return new PriceSeries(ticker, dateRange, data);
    }

    private double basePriceFor(Ticker ticker) {
        return 80.0 + Math.abs(ticker.getSymbol().hashCode() % 120);
    }
}
