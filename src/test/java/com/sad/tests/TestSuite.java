package com.sad.tests;

import com.sad.app.ComparisonService;
import com.sad.app.PriceManagementService;
import com.sad.domain.ComparisonResult;
import com.sad.domain.DateRange;
import com.sad.domain.Portfolio;
import com.sad.domain.PricePoint;
import com.sad.domain.PriceSeries;
import com.sad.domain.Ticker;
import com.sad.ports.IPriceRepository;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class TestSuite {

    public static void main(String[] args) {
        System.out.println("Running Share Comparison coursework tests...\n");

        testTickerNormalisationAndEquality();
        testDateRangeRejectsMoreThanTwoYears();
        testPortfolioRejectsDuplicateTickers();
        testTwoTickerComparisonUseCase();

        System.out.println("\nAll tests passed.");
    }

    private static void testTickerNormalisationAndEquality() {
        Ticker lowerCaseTicker = new Ticker(" aapl ");
        Ticker upperCaseTicker = new Ticker("AAPL");

        assertEquals("AAPL", lowerCaseTicker.getSymbol(), "Ticker should have been trimmed and uppercased.");
        assertTrue(lowerCaseTicker.equals(upperCaseTicker), "Tickers with the same symbol should have been equal.");
        assertEquals(lowerCaseTicker.hashCode(), upperCaseTicker.hashCode(), "Equal tickers should have had have same hashCode.");

        pass("Ticker stuff");
    }

    private static void testDateRangeRejectsMoreThanTwoYears() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DateRange(LocalDate.of(2022, 1, 1), LocalDate.of(2025, 1, 2)),
                "DateRange should have rejected ranges longer than two years."
        );

        pass("DateRange two-year validation");
    }

    private static void testPortfolioRejectsDuplicateTickers() {
        Portfolio portfolio = new Portfolio("alice", new LinkedHashSet<>());

        boolean firstAdd = portfolio.addTicker(new Ticker("AAPL"));
        boolean secondAdd = portfolio.addTicker(new Ticker("aapl"));

        assertTrue(firstAdd, "First ticker add should return true.");
        assertFalse(secondAdd, "Duplicate ticker add should return false.");
        assertEquals(1, portfolio.getTickers().size(), "Portfolio should contain only one unique ticker.");

        pass("Portfolio duplicate ticker prevention");
    }

    /**
     * This is the main test for comparing two tickers over a date range.
     *
     * It uses a fake repository instead of the real API so the test is stable and does not depend
     * on internet
     */
    private static void testTwoTickerComparisonUseCase() {
        DateRange dateRange = new DateRange(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 3));

        IPriceRepository fakeRepository = new FakePriceRepository();
        PriceManagementService priceManagementService = new PriceManagementService(fakeRepository);
        ComparisonService comparisonService = new ComparisonService(priceManagementService);

        ComparisonResult result = comparisonService.createComparisonResult(
                List.of(new Ticker("AAPL"), new Ticker("MSFT")),
                dateRange
        );

        assertEquals(2, result.getSeriesList().size(), "Comparison should contain two price series.");
        assertEquals("AAPL", result.getSeriesList().get(0).getTicker().getSymbol(), "First result should be AAPL.");
        assertEquals("MSFT", result.getSeriesList().get(1).getTicker().getSymbol(), "Second result should be MSFT.");
        assertEquals(3, result.getSeriesList().get(0).getPrices().size(), "AAPL should have three price points.");
        assertEquals(3, result.getSeriesList().get(1).getPrices().size(), "MSFT should have three price points.");

        pass("Two ticker comparison use case");
    }

    private static class FakePriceRepository implements IPriceRepository {
        @Override
        public PriceSeries loadPrices(Ticker ticker, DateRange dateRange) {
            if (ticker.equals(new Ticker("AAPL"))) {
                return new PriceSeries(
                        ticker,
                        dateRange,
                        List.of(
                                new PricePoint(LocalDate.of(2024, 1, 1), 180.00),
                                new PricePoint(LocalDate.of(2024, 1, 2), 182.50),
                                new PricePoint(LocalDate.of(2024, 1, 3), 181.25)
                        )
                );
            }

            if (ticker.equals(new Ticker("MSFT"))) {
                return new PriceSeries(
                        ticker,
                        dateRange,
                        List.of(
                                new PricePoint(LocalDate.of(2024, 1, 1), 370.00),
                                new PricePoint(LocalDate.of(2024, 1, 2), 371.50),
                                new PricePoint(LocalDate.of(2024, 1, 3), 375.00)
                        )
                );
            }

            throw new IllegalArgumentException("Unexpected test ticker: " + ticker.getSymbol());
        }

        @Override
        public void storePrices(PriceSeries series) {
            // Not needed for this test because it only verifies comparison retrieval.
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " Expected: " + expected + ", actual: " + actual);
        }
    }

    private static void assertThrows(Class<? extends Throwable> expectedType, Runnable action, String message) {
        try {
            action.run();
        } catch (Throwable actual) {
            if (expectedType.isInstance(actual)) {
                return;
            }
            throw new AssertionError(message + " Expected exception: " + expectedType.getSimpleName()
                    + ", actual exception: " + actual.getClass().getSimpleName());
        }
        throw new AssertionError(message + " Expected exception: " + expectedType.getSimpleName() + ", but nothing was thrown.");
    }

    private static void pass(String testName) {
        System.out.println("PASS: " + testName);
    }
}
