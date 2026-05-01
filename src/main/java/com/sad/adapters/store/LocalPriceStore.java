package com.sad.adapters.store;

import com.sad.domain.DateRange;
import com.sad.domain.PricePoint;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.ILocalPriceStore;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalPriceStore implements ILocalPriceStore {
    private final Map<String, PriceSeries> memoryCache = new HashMap<>();
    private final Path storageDirectory;

    public LocalPriceStore() {
        this(Path.of("data", "prices"));
    }

    public LocalPriceStore(Path storageDirectory) {
        this.storageDirectory = storageDirectory;
        try {
            Files.createDirectories(storageDirectory);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not create local price storage directory.", ex);
        }
    }

    private String key(Ticker ticker, DateRange range) {
        return ticker.getSymbol() + "|" + range.getStartDate() + "|" + range.getEndDate();
    }

    private Path fileFor(Ticker ticker, DateRange range) {
        String fileName = ticker.getSymbol() + "_" + range.getStartDate() + "_" + range.getEndDate() + ".csv";
        return storageDirectory.resolve(fileName);
    }

    @Override
    public PriceSeries read(Ticker ticker, DateRange dateRange) {
        String key = key(ticker, dateRange);
        if (memoryCache.containsKey(key)) {
            return memoryCache.get(key);
        }

        Path file = fileFor(ticker, dateRange);
        if (Files.notExists(file)) {
            return null;
        }

        try {
            List<PricePoint> points = new ArrayList<>();
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length >= 2) {
                    points.add(new PricePoint(LocalDate.parse(parts[0]), Double.parseDouble(parts[1])));
                }
            }
            if (points.isEmpty()) {
                return null;
            }
            PriceSeries series = new PriceSeries(ticker, dateRange, points);
            memoryCache.put(key, series);
            return series;
        } catch (IOException | RuntimeException ex) {
            System.out.println("[LocalPriceStore] Could not read cached prices. Fetching fresh data instead.");
            return null;
        }
    }

    @Override
    public void write(PriceSeries series) {
        memoryCache.put(key(series.getTicker(), series.getDateRange()), series);
        Path file = fileFor(series.getTicker(), series.getDateRange());
        StringBuilder csv = new StringBuilder("date,close\n");
        for (PricePoint point : series.getPrices()) {
            csv.append(point.getDate()).append(',').append(point.getPrice()).append('\n');
        }
        try {
            Files.createDirectories(storageDirectory);
            Files.writeString(file, csv.toString(), StandardCharsets.UTF_8);
            System.out.println("[LocalPriceStore] Saved " + series.getTicker().getSymbol() + " price series locally.");
        } catch (IOException ex) {
            throw new IllegalStateException("Could not write local price data.", ex);
        }
    }
}
