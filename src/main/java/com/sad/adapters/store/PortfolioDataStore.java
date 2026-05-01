package com.sad.adapters.store;

import com.sad.domain.Portfolio;
import com.sad.domain.Ticker;
import com.sad.ports.IPortfolioDataStore;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PortfolioDataStore implements IPortfolioDataStore {
    private final Map<String, Portfolio> portfolios = new HashMap<>();
    private final Path filePath;

    public PortfolioDataStore() {
        this(Path.of("data", "portfolios.json"));
    }

    public PortfolioDataStore(Path filePath) {
        this.filePath = filePath;
        load();
    }

    @Override
    public Portfolio findByUsername(String username) {
        String key = normalise(username);
        return portfolios.computeIfAbsent(key, name -> new Portfolio(name, new LinkedHashSet<>()));
    }

    @Override
    public void addTicker(String username, Ticker ticker) {
        Portfolio portfolio = findByUsername(username);
        boolean added = portfolio.addTicker(ticker);

        if (added) {
            save();
            System.out.println("[PortfolioDataStore] Added " + ticker.getSymbol() + " to " + username + "'s portfolio.");
        } else {
            System.out.println("[PortfolioDataStore] " + ticker.getSymbol() + " is already in " + username + "'s portfolio.");
        }
    }

    private void load() {
        try {
            Files.createDirectories(filePath.getParent());

            if (Files.notExists(filePath)) {
                save();
                return;
            }

            for (String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)) {
                String cleaned = line.trim();

                if (cleaned.endsWith(",")) {
                    cleaned = cleaned.substring(0, cleaned.length() - 1);
                }

                if (!cleaned.startsWith("{") || !cleaned.endsWith("}")) {
                    continue;
                }

                String username = extract(cleaned, "username");
                String tickerCsv = extract(cleaned, "tickers");

                if (username == null) {
                    continue;
                }

                Set<Ticker> tickers = new LinkedHashSet<>();

                if (tickerCsv != null && !tickerCsv.isBlank()) {
                    for (String symbol : tickerCsv.split(",")) {
                        if (!symbol.isBlank()) {
                            tickers.add(new Ticker(symbol));
                        }
                    }
                }

                String normalisedUsername = normalise(username);
                portfolios.put(normalisedUsername, new Portfolio(normalisedUsername, tickers));
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load portfolio data.", ex);
        }
    }

    private void save() {
        try {
            Files.createDirectories(filePath.getParent());

            StringBuilder json = new StringBuilder();
            json.append("[\n");

            int portfolioIndex = 0;

            for (Portfolio portfolio : portfolios.values()) {
                StringBuilder tickers = new StringBuilder();

                int tickerIndex = 0;
                for (Ticker ticker : portfolio.getTickers()) {
                    if (tickerIndex > 0) {
                        tickers.append(",");
                    }
                    tickers.append(ticker.getSymbol());
                    tickerIndex++;
                }

                json.append("  {\"username\":\"")
                        .append(escape(portfolio.getUsername()))
                        .append("\", \"tickers\":\"")
                        .append(escape(tickers.toString()))
                        .append("\"}");

                if (++portfolioIndex < portfolios.size()) {
                    json.append(",");
                }

                json.append("\n");
            }

            json.append("]\n");

            Files.writeString(filePath, json.toString(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not save portfolio data.", ex);
        }
    }

    private String normalise(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must not be empty.");
        }
        return username.trim().toLowerCase();
    }

    private String extract(String object, String key) {
        String search = "\"" + key + "\":\"";
        int start = object.indexOf(search);

        if (start < 0) {
            search = "\"" + key + "\": \"";
            start = object.indexOf(search);
        }

        if (start < 0) {
            return null;
        }

        start += search.length();

        int end = object.indexOf("\"", start);

        if (end < 0) {
            return null;
        }

        return object.substring(start, end).replace("\\\"", "\"");
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}