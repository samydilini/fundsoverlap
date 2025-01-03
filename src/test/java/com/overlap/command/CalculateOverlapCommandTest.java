package com.overlap.command;

import com.overlap.exception.FundsOverlapException;
import com.overlap.model.Fund;
import com.overlap.model.FundSession;
import com.overlap.service.FundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CalculateOverlapCommandTest {
    @Mock
    private FundService fundService;
    @InjectMocks
    private CalculateOverlapCommand calculateOverlapCommand;

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        outContent.reset();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void printPercentageWhenTokenIsFound() throws FundsOverlapException {
        List<String> commandFunds = Collections.singletonList("fund2");
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
        when(fundService.findFunds(commandFunds, fundSession)).thenReturn(portfolioFunds);

        FundSession responseFundSession = calculateOverlapCommand.execute(commandFunds, fundSession);

        assertSame(fundSession, responseFundSession);
        assertEquals("fund1 fund1 100.00%fund1 fund2 66.67%",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));
        verify(fundService).findFunds(commandFunds, fundSession);
    }

    @Test
    void printFoundNotFoundWhenTokenIsNew() throws FundsOverlapException {
        List<String> commandFunds = Collections.singletonList("fund2");
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
        when(fundService.findFunds(commandFunds, fundSession)).thenReturn(new ArrayList<>());

        FundSession responseFundSession = calculateOverlapCommand.execute(Collections.singletonList("fund2"), fundSession);

        assertSame(fundSession, responseFundSession);
        assertEquals("FUND_NOT_FOUND",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));
        verify(fundService).findFunds(commandFunds, fundSession);
    }
}