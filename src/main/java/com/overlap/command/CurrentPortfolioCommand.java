package com.overlap.command;

import com.overlap.exception.FundsOverlapException;
import com.overlap.model.Fund;
import com.overlap.model.FundSession;
import com.overlap.service.FundService;

import java.util.List;

/**
 * Handles current portfolio command.
 */
public class CurrentPortfolioCommand implements InputCommand {
    private final FundService fundService;


    public CurrentPortfolioCommand(FundService fundService) {
        this.fundService = fundService;
    }

    /**
     * Sets current portfolio. This is where values are first added to the session.
     *
     * @param commandFunds all the fund names to be added to the portfolio
     * @param fundSession  empty session with empty portfolio
     * @return session with the new portfolios added
     * @throws FundsOverlapException if there are no available funds
     */
    @Override
    public FundSession execute(List<String> commandFunds, FundSession fundSession) throws FundsOverlapException {
        List<Fund> portfolioFunds = fundService.findFunds(commandFunds, fundSession);
        if (portfolioFunds.isEmpty()) {
            String message = "Error: There are no available funds.";
            System.out.println(message);
            throw new FundsOverlapException(message);
        }
        fundSession.setPortfolioFunds(portfolioFunds);
        return fundSession;
    }
}
