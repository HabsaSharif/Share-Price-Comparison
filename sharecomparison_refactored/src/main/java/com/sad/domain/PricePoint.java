package com.sad.domain;

import java.time.LocalDate;  // Using LocalDate avoids manual date handling errors and prevents invalid calendar values.

// NOT LocalDateTime because stocks change per day, so time isn't needed.

/*
 PricePoint class will tell us how much a share cost on a specific day.
 The date and price form a single domain fact and should not exist independently.
 It is included in the domain package because it's represents a real concept - the price of stock on a certain day.
*/

public class PricePoint {
    private final LocalDate date;
    private final double price;
    //Privatised for encapsulation.

    //Constructor to initialize Price Point object.
    public PricePoint(LocalDate date, double price) {
        this.date = date;
        this.price = price;
    }

    //Getters to access from outside of class.
    public LocalDate getDate() { return date; }
    public double getPrice() { return price; }
}
