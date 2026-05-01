package com.sad.adapters.external;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

 //Class to contact EMD, doesnt know abt app domain objects, adapter is responsible for translating this raw API response into domain objects.

public class ExternalAPI {
    private static final DateTimeFormatter STOOQ_DATE = DateTimeFormatter.BASIC_ISO_DATE;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(8))
            .build();

    public String fetchDailyPricesCsv(String symbol, LocalDate startDate, LocalDate endDate)
            throws IOException, InterruptedException {

        String url = "https://stooq.com/q/d/l/?s="
                + URLEncoder.encode(toStooqSymbol(symbol), StandardCharsets.UTF_8)
                + "&d1=" + startDate.format(STOOQ_DATE)
                + "&d2=" + endDate.format(STOOQ_DATE)
                + "&i=d";

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(12))
                .GET()
                .header("User-Agent", "SAD-Coursework-ShareComparison/1.0")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("External market API HTTP status: " + response.statusCode());
        }

        return response.body();
    }

    private String toStooqSymbol(String symbol) {
        String lower = symbol.toLowerCase().trim();
        if (lower.contains(".")) {
            return lower;
        }
        return lower + ".us";
    }
}
