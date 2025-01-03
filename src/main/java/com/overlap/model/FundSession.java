package com.overlap.model;

import java.util.List;

/**
 * This class holds the session details for funds.
 */
public class FundSession {
    /**
     * The funds for the current portfolio.
     */
    private List<Fund> portfolioFunds;

    /**
     * The available funds including the changes done by commands.
     */
    private List<Fund> availableFunds;

    /**
     * Constructs a new FundSession with the specified portfolio and available funds.
     *
     * @param portfolioFunds the funds for the current portfolio
     * @param availableFunds the available funds including the changes done by commands
     */
    public FundSession(List<Fund> portfolioFunds, List<Fund> availableFunds) {
        this.portfolioFunds = portfolioFunds;
        this.availableFunds = availableFunds;
    }

    /**
     * Sets the funds for the current portfolio.
     *
     * @param portfolioFunds the funds for the current portfolio
     */
    public void setPortfolioFunds(List<Fund> portfolioFunds) {
        this.portfolioFunds = portfolioFunds;
    }

    /**
     * Sets the available funds including the changes done by commands.
     *
     * @param availableFunds the available funds
     */
    public void setAvailableFunds(List<Fund> availableFunds) {
        this.availableFunds = availableFunds;
    }

    /**
     * Returns the funds for the current portfolio.
     *
     * @return the funds for the current portfolio
     */
    public List<Fund> getPortfolioFunds() {
        return portfolioFunds;
    }

    /**
     * Returns the available funds including the changes done by commands.
     *
     * @return the available funds
     */
    public List<Fund> getAvailableFunds() {
        return availableFunds;
    }
}