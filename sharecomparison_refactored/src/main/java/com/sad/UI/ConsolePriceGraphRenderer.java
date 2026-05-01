package com.sad.UI;

import com.sad.domain.ComparisonResult;
import com.sad.domain.PricePoint;
import com.sad.domain.PriceSeries;
import com.sad.ports.IViewPriceGraph;

import java.util.ArrayList;
import java.util.List;

public class ConsolePriceGraphRenderer implements IViewPriceGraph {
    private static final int MAX_GRAPH_POINTS = 48;
    private static final String GRAPH_LEVELS = "▁▂▃▄▅▆▇█";

    @Override
    public void viewPriceGraph(ComparisonResult result) {
        List<PriceSeries> seriesList = result.getSeriesList();

        if (seriesList == null || seriesList.isEmpty()) {
            System.out.println("\nNo price data available.");
            return;
        }

        System.out.println("\n============================================================");
        System.out.println("                  Share Price Performance");
        System.out.println("============================================================");

        if (seriesList.size() == 1) {
            renderSingleSeries(seriesList.get(0));
        } else {
            renderComparison(seriesList);
        }
    }

    private void renderSingleSeries(PriceSeries series) {
        List<PricePoint> points = series.getPrices();

        if (points == null || points.isEmpty()) {
            System.out.println("\n" + series.getTicker().getSymbol() + ": no price data available.");
            return;
        }

        Summary summary = summarize(series);

        System.out.println("\nTicker: " + series.getTicker().getSymbol());
        System.out.println("Range : " + series.getDateRange().getStartDate() + " to " + series.getDateRange().getEndDate());
        System.out.println();
        System.out.printf("%-14s %12s%n", "Metric", "Value");
        System.out.println("----------------------------");
        System.out.printf("%-14s %12.2f%n", "Start price", summary.startPrice);
        System.out.printf("%-14s %12.2f%n", "End price", summary.endPrice);
        System.out.printf("%-14s %+11.2f%%%n", "Change", summary.percentChange);
        System.out.printf("%-14s %12.2f%n", "Lowest", summary.minPrice);
        System.out.printf("%-14s %12.2f%n", "Highest", summary.maxPrice);

        System.out.println("\nTrend:");
        System.out.println(generateMiniGraph(points));
        System.out.println("Start " + points.get(0).getDate() + "  →  End " + points.get(points.size() - 1).getDate());
    }

    private void renderComparison(List<PriceSeries> seriesList) {
        System.out.println();
        System.out.printf("%-10s %-50s %12s %12s %12s%n", "Ticker", "Trend", "Start", "End", "Change");
        System.out.println("------------------------------------------------------------------------------------------------");

        for (PriceSeries series : seriesList) {
            List<PricePoint> points = series.getPrices();

            if (points == null || points.isEmpty()) {
                System.out.printf("%-10s %-50s %12s %12s %12s%n",
                        series.getTicker().getSymbol(), "No data", "-", "-", "-");
                continue;
            }

            Summary summary = summarize(series);
            System.out.printf("%-10s %-50s %12.2f %12.2f %+11.2f%%%n",
                    series.getTicker().getSymbol(),
                    generateMiniGraph(points),
                    summary.startPrice,
                    summary.endPrice,
                    summary.percentChange);
        }
    }

    private String generateMiniGraph(List<PricePoint> points) {
        List<Double> sampledPrices = samplePrices(points);

        double min = sampledPrices.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double max = sampledPrices.stream().mapToDouble(Double::doubleValue).max().orElse(0);

        if (sampledPrices.isEmpty()) {
            return "";
        }

        if (Double.compare(min, max) == 0) {
            return "█".repeat(sampledPrices.size());
        }

        StringBuilder graph = new StringBuilder();
        for (double price : sampledPrices) {
            double normalized = (price - min) / (max - min);
            int index = (int) Math.round(normalized * (GRAPH_LEVELS.length() - 1));
            index = Math.max(0, Math.min(index, GRAPH_LEVELS.length() - 1));
            graph.append(GRAPH_LEVELS.charAt(index));
        }

        return graph.toString();
    }

    private List<Double> samplePrices(List<PricePoint> points) {
        List<Double> sampled = new ArrayList<>();
        int step = Math.max(1, (int) Math.ceil(points.size() / (double) MAX_GRAPH_POINTS));

        for (int i = 0; i < points.size(); i += step) {
            sampled.add(points.get(i).getPrice());
        }

        PricePoint lastPoint = points.get(points.size() - 1);
        double lastPrice = lastPoint.getPrice();
        if (sampled.isEmpty() || Double.compare(sampled.get(sampled.size() - 1), lastPrice) != 0) {
            sampled.add(lastPrice);
        }

        return sampled;
    }

    private Summary summarize(PriceSeries series) {
        List<PricePoint> points = series.getPrices();
        double start = points.get(0).getPrice();
        double end = points.get(points.size() - 1).getPrice();
        double min = points.stream().mapToDouble(PricePoint::getPrice).min().orElse(start);
        double max = points.stream().mapToDouble(PricePoint::getPrice).max().orElse(start);
        double percent = start == 0 ? 0 : ((end - start) / start) * 100.0;
        return new Summary(start, end, min, max, percent);
    }

    private static class Summary {
        private final double startPrice;
        private final double endPrice;
        private final double minPrice;
        private final double maxPrice;
        private final double percentChange;

        private Summary(double startPrice, double endPrice, double minPrice, double maxPrice, double percentChange) {
            this.startPrice = startPrice;
            this.endPrice = endPrice;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
            this.percentChange = percentChange;
        }
    }
}
