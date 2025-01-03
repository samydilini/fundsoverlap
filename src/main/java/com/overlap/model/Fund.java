package com.overlap.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Fund to hold name and the list of related stocks.
 */
public class Fund {
    /**
     * The name of the fund.
     */
    String name;

    /**
     * The list of stocks related to the fund.
     */
    List<String> stocks;

    /**
     * Constructs a new Fund with the specified name.
     *
     * @param name the name of the fund
     */
    public Fund(String name) {
        this.name = name;
        this.stocks = new ArrayList<>();
    }

    /**
     * Returns the name of the fund.
     *
     * @return the name of the fund
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of stocks related to the fund.
     * If the list is null, initializes it to an empty list.
     *
     * @return the list of stocks
     */
    public List<String> getStocks() {
        if (stocks == null) {
            this.stocks = new ArrayList<>();
        }
        return stocks;
    }

    /**
     * Sets the list of stocks related to the fund.
     *
     * @param stocks the list of stocks
     */
    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }

    /**
     * Adds a stock to the list of stocks related to the fund.
     *
     * @param stock the stock to add
     */
    public void addStock(String stock) {
        this.stocks.add(stock);
    }
}