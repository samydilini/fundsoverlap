package com.overlap.command;

import com.overlap.model.Fund;
import com.overlap.model.FundSession;

import java.util.List;
import java.util.Optional;

/**
 * Command to handle adding stocks to a given fund.
 */
public class AddStockCommand implements InputCommand {

    /**
     * Executes the add stock command. If the fund is not found in the available funds list,
     * it prints out "FUND_NOT_FOUND".
     *
     * @param tokens      A list of strings where the first element is the fund name and the second element is the stock name.
     * @param fundSession The current session information containing available funds.
     * @return The updated FundSession after attempting to add the stock.
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
