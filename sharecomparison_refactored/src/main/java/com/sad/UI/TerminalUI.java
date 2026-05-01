package com.sad.UI;

import com.sad.UI.mvc.AuthController;
import com.sad.UI.mvc.ComparisonGraphView;
import com.sad.UI.mvc.LoginView;
import com.sad.UI.mvc.PortfolioController;
import com.sad.UI.mvc.PortfolioView;
import com.sad.UI.mvc.SessionModel;
import com.sad.domain.ComparisonResult;
import com.sad.domain.Portfolio;
import com.sad.domain.Ticker;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class TerminalUI {
    private final Scanner scanner = new Scanner(System.in);
    private final LoginView loginView;
    private final AuthController authController;
    private final PortfolioView portfolioView;
    private final PortfolioController portfolioController;
    private final ComparisonGraphView comparisonGraphView;
    private final SessionModel sessionModel;

    public TerminalUI(
            LoginView loginView,
            AuthController authController,
            PortfolioView portfolioView,
            PortfolioController portfolioController,
            ComparisonGraphView comparisonGraphView,
            SessionModel sessionModel
    ) {
        this.loginView = loginView;
        this.authController = authController;
        this.portfolioView = portfolioView;
        this.portfolioController = portfolioController;
        this.comparisonGraphView = comparisonGraphView;
        this.sessionModel = sessionModel;
    }

    public void run() {
        System.out.println("\n========================================");
        System.out.println(" Welcome to Share Price Comparison");
        System.out.println("========================================");
        System.out.println("Preset accounts: alice/pass123, bob/pass123, charlie/pass123");

        boolean running = true;
        while (running) {
            try {
                running = sessionModel.isLoggedIn() ? loggedInMenu() : guestMenu();
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Goodbye.");
    }

    private boolean guestMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1. Compare shares without logging in");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        String choice = prompt("Choose: ");
        switch (choice) {
            case "1" -> compare(false);
            case "2" -> login();
            case "3" -> { return false; }
            default -> System.out.println("Please choose 1, 2, or 3.");
        }
        return true;
    }

    private boolean loggedInMenu() {
        System.out.println("\nWelcome, " + sessionModel.getCurrentUser().getDisplayName());
        System.out.println("1. Compare shares");
        System.out.println("2. View portfolio");
        System.out.println("3. Add ticker to portfolio");
        System.out.println("4. View saved ticker performance");
        System.out.println("5. Logout");
        System.out.println("6. Exit");
        String choice = prompt("Choose: ");
        switch (choice) {
            case "1" -> compare(true);
            case "2" -> portfolioView.showPortfolio();
            case "3" -> addTickerToPortfolio();
            case "4" -> viewSavedTickerPerformance();
            case "5" -> authController.logout();
            case "6" -> { return false; }
            default -> System.out.println("Please choose a valid menu option.");
        }
        return true;
    }

    private void login() {
        String username = prompt("Username: ");
        String password = prompt("Password: ");
        loginView.submitLogin(username, password);
    }

    private void compare(boolean allowPortfolioSave) {
        String firstTicker = prompt("First ticker: ");
        String secondTicker = prompt("Second ticker (optional - press Enter to skip): ");
        LocalDate startDate = readDate("Start date (YYYY-MM-DD): ");
        LocalDate endDate = readDate("End date (YYYY-MM-DD): ");

        List<String> symbols = new ArrayList<>();
        symbols.add(firstTicker);
        if (!secondTicker.isBlank()) {
            symbols.add(secondTicker);
        }

        ComparisonResult result = comparisonGraphView.requestComparison(symbols, startDate, endDate);

        if (allowPortfolioSave && result.getSeriesList().size() == 1) {
            String save = prompt("Save " + firstTicker.trim().toUpperCase() + " to portfolio? (y/n): ");
            if (save.equalsIgnoreCase("y")) {
                portfolioController.addTicker(firstTicker);
            }
        }
    }

    private void addTickerToPortfolio() {
        String ticker = prompt("Ticker to save: ");
        portfolioController.addTicker(ticker);
    }

    private void viewSavedTickerPerformance() {
        Portfolio portfolio = portfolioController.getCurrentPortfolio();
        if (portfolio.getTickers().isEmpty()) {
            System.out.println("Your portfolio is empty. Add a ticker first.");
            return;
        }
        System.out.println("\nSaved tickers:");

        List<Ticker> tickerList = new ArrayList<>(portfolio.getTickers());

        for (int i = 0; i < tickerList.size(); i++) {
            System.out.println((i + 1) + ". " + tickerList.get(i).getSymbol());
        }

        int index = readNumber("Choose ticker number: ", 1, tickerList.size()) - 1;

        Ticker ticker = tickerList.get(index);

        LocalDate startDate = readDate("Start date (YYYY-MM-DD): ");
        LocalDate endDate = readDate("End date (YYYY-MM-DD): ");

        comparisonGraphView.requestComparison(
                List.of(ticker.getSymbol()),
                startDate,
                endDate
        );
    }

    private String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private LocalDate readDate(String message) {
        while (true) {
            try {
                return LocalDate.parse(prompt(message));
            } catch (DateTimeParseException ex) {
                System.out.println("Use date format YYYY-MM-DD, e.g. 2025-01-31.");
            }
        }
    }

    private int readNumber(String message, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(prompt(message));
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Enter a number from " + min + " to " + max + ".");
        }
    }
}
