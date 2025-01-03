package com.overlap.command;

import com.overlap.exception.FundsOverlapException;
import com.overlap.model.Fund;
import com.overlap.service.FundService;
import com.overlap.model.FundSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command to handle calucate command
 */
public class CalculateOverlapCommand implements InputCommand {
    private final FundService fundService;

    public CalculateOverlapCommand(FundService fundService) {
        this.fundService = fundService;
    }

    /**
     * Will calculate and print the overlap of a fund against portfolioFunds. Will print FUND_NOT_FOUND if token doesn't belong to any available funds
     *
     * @param tokens      has the fund to calculate overlap
     * @param fundSession has portfolioFunds for the current session
     * @return the same session
     * @throws FundsOverlapException
     */
    @Override
    public FundSession execute(List<String> tokens, FundSession fundSession) throws FundsOverlapException {
        for (String token : tokens) {
            List<Fund> fundsToCheck = fundService.findFunds(Collections.singletonList(token), fundSession);
            Fund fundToCheck;
            if (fundsToCheck.isEmpty()) {
                System.out.println("FUND_NOT_FOUND");
                return fundSession;
            } else {
                fundToCheck = fundsToCheck.get(0);
            }

            for (Fund fund : fundSession.getPortfolioFunds()) {
                List<String> common = new ArrayList<>(fundToCheck.getStocks());
                common.retainAll(fund.getStocks());
                double commonStocks = common.size();
                double percentage = 2 * commonStocks / (fundToCheck.getStocks().size() + fund.getStocks().size()) * 100;
                System.out.println(fundToCheck.getName() + " " + fund.getName() + " " + String.format("%.2f", percentage) + "%");
            }
        }
        return fundSession;
    }
}
