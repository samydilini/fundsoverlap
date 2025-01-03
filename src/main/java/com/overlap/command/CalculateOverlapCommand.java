package com.overlap.command;

import com.overlap.exception.FundsOverlapException;
import com.overlap.model.Fund;
import com.overlap.service.FundService;
import com.overlap.model.FundSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command to handle calculate command.
 */
public class CalculateOverlapCommand implements InputCommand {
    private final FundService fundService;

    /**
     * Constructor to initialize CalculateOverlapCommand with a FundService.
     *
     * @param fundService the service to handle fund-related operations
     */
    public CalculateOverlapCommand(FundService fundService) {
        this.fundService = fundService;
    }

    /**
     * Will calculate and print the overlap of a fund against portfolioFunds.
     * Will print FUND_NOT_FOUND if token doesn't belong to any available funds.
     *
     * @param tokens      has the fund to calculate overlap
     * @param fundSession has portfolioFunds for the current session
     * @return the same session
     * @throws FundsOverlapException if there is an error during the overlap calculation
     */
    @Override
    public FundSession execute(List<String> tokens, FundSession fundSession) throws FundsOverlapException {
        for (String token : tokens) {
            List<Fund> fundsToCheck = fundService.findFunds(Collections.singletonList(token), fundSession);
            if (fundsToCheck.isEmpty()) {
                System.out.println("FUND_NOT_FOUND");
                return fundSession;
            }

            Fund fundToCheck = fundsToCheck.get(0);
            fundSession.getPortfolioFunds().forEach(fund -> {
                double percentage = calculateOverlapPercentage(fundToCheck, fund);
                if (percentage > 0)
                    System.out.println(fundToCheck.getName() + " " + fund.getName() + " " + String.format("%.2f", percentage) + "%");
            });
        }
        return fundSession;
    }

    /**
     * Calculates the overlap percentage between two funds.
     *
     * @param fund1 the first fund
     * @param fund2 the second fund
     * @return the overlap percentage
     */
    private double calculateOverlapPercentage(Fund fund1, Fund fund2) {
        List<String> common = new ArrayList<>(fund1.getStocks());
        common.retainAll(fund2.getStocks());
        double commonStocks = common.size();
        return 2 * commonStocks / (fund1.getStocks().size() + fund2.getStocks().size()) * 100;
    }
}
