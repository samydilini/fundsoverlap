package com.overlap.model;

import java.util.List;

/**
 * This will have the session details.
 */
public class FundSession {
    /**
     * Funds for the current portfolio
     */
    private List<Fund> portfolioFunds;
    /**
     * available funds including the changes done by commands
     */
    private List<Fund> availableFunds;

    public FundSession(List<Fund> portfolioFunds, List<Fund> availableFunds) {
        this.portfolioFunds = portfolioFunds;
        this.availableFunds = availableFunds;
    }


    public void setPortfolioFunds(List<Fund> portfolioFunds) {
        this.portfolioFunds = portfolioFunds;
    }

    public void setAvailableFunds(List<Fund> availableFunds) {
        this.availableFunds= availableFunds;
    }

    public List<Fund> getPortfolioFunds() {
        return portfolioFunds;
    }

    public List<Fund> getAvailableFunds() {
        return availableFunds;
    }
}
