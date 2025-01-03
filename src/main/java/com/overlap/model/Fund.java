package com.overlap.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Fund to hold name and the list of related stocks
 */
public class Fund {
    String name;
    List<String> stocks;

    public Fund(String name) {
        this.name = name;
        this.stocks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getStocks() {
        if (stocks == null) {
            this.stocks = new ArrayList<>();
        }
        return stocks;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }

    public void addStock(String stock) {
        this.stocks.add(stock);
    }
}
