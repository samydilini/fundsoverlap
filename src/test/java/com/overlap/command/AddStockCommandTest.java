package com.overlap.command;

import com.overlap.model.Fund;
import com.overlap.model.FundSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class AddStockCommandTest {

    AddStockCommand addStockCommand;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        addStockCommand = new AddStockCommand();
        outContent.reset();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void addFundWhenFundIsFound() {
        List<String> commandFunds = Arrays.asList("fund2", "stock3");
        List<Fund> portfolioFunds = new ArrayList<>();
        Fund fund1 = new Fund("fund1");
        fund1.addStock("stock1");
        Fund fund2 = new Fund("fund2");
        fund2.addStock("stock1");
        fund2.addStock("stock2");
        portfolioFunds.add(fund1);
        portfolioFunds.add(fund2);
        List<Fund> availableFunds = new ArrayList<>();
        availableFunds.add(fund1);
        availableFunds.add(fund2);
        availableFunds.add(new Fund("fund3"));
        availableFunds.add(new Fund("fund4"));
        FundSession fundSession = new FundSession(portfolioFunds, availableFunds);

        FundSession responseFundSession = addStockCommand.execute(commandFunds, fundSession);

        assertEquals(4, responseFundSession.getAvailableFunds().size());
        Fund secondFund = responseFundSession.getAvailableFunds().get(1);
        assertEquals(3, secondFund.getStocks().size());
        assertEquals("stock3", secondFund.getStocks().get(2));
    }

    @Test
    void printWhenFundIsNotFound() {
        List<String> commandFunds = Arrays.asList("fundNew", "stock3");
        List<Fund> portfolioFunds = new ArrayList<>();
        Fund fund1 = new Fund("fund1");
        fund1.addStock("stock1");
        Fund fund2 = new Fund("fund2");
        fund2.addStock("stock1");
        fund2.addStock("stock2");
        portfolioFunds.add(fund1);
        portfolioFunds.add(fund2);
        List<Fund> availableFunds = new ArrayList<>();
        availableFunds.add(fund1);
        availableFunds.add(fund2);
        availableFunds.add(new Fund("fund3"));
        availableFunds.add(new Fund("fund4"));
        FundSession fundSession = new FundSession(portfolioFunds, availableFunds);

        FundSession responseFundSession = addStockCommand.execute(commandFunds, fundSession);

        assertSame(responseFundSession, fundSession);
        assertEquals("FUND_NOT_FOUND",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));
    }
}