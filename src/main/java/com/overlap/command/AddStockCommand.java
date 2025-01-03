package com.overlap.command;

import com.overlap.model.Fund;
import com.overlap.model.FundSession;

import java.util.List;
import java.util.Optional;

/**
 * Command to handle adding stocks to a given fund
 */
public class AddStockCommand implements InputCommand {

    /**
     * Is used to execute add command. will print out FUND_NOT_FOUND if the fund is not in the available funds list.
     *
     * @param tokens      this will have the fund and the stock name
     * @param fundSession The current session information.
     * @return will return the updated session
     */
    @Override
    public FundSession execute(List<String> tokens, FundSession fundSession) {
        Optional<Fund> matchingFund = fundSession.getAvailableFunds()
                .stream()
                .filter(fund -> fund.getName().equals(tokens.get(0)))
                .findFirst();

        if (matchingFund.isPresent()) {
            matchingFund.get().addStock(tokens.get(1));
        } else {
            System.out.println("FUND_NOT_FOUND");
        }
        return fundSession;
    }
}
