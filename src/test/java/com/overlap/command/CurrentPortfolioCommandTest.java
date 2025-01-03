package com.overlap.command;

import com.overlap.exception.FundsOverlapException;
import com.overlap.model.Fund;
import com.overlap.service.FundService;
import com.overlap.model.FundSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CurrentPortfolioCommandTest {
    @Mock
    private FundService fundService;
    @InjectMocks
    CurrentPortfolioCommand currentPortfolioCommand;

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        outContent.reset();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void returnIfPortfolioFundsIsFound() throws FundsOverlapException {
        List<String> commandFunds = Arrays.asList("fund1", "fund2");
        List<Fund> portfolioFunds = new ArrayList<>();
        portfolioFunds.add(new Fund("fund1"));
        portfolioFunds.add(new Fund("fund2"));
        FundSession fundSession = new FundSession(portfolioFunds, new ArrayList<>());
        when(fundService.findFunds(commandFunds, fundSession)).thenReturn(portfolioFunds);

        FundSession responseFundSession = currentPortfolioCommand.execute(commandFunds,  fundSession);

        assertEquals(portfolioFunds, responseFundSession.getPortfolioFunds());
        verify(fundService).findFunds(commandFunds, fundSession);
    }

    @Test
    public void ThrowErrorIfPortfolioFundsNotFound() throws FundsOverlapException {
        List<String> commandFunds = Arrays.asList("fund1", "fund2");
        List<Fund> portfolioFunds = new ArrayList<>();
        FundSession fundSession = new FundSession(portfolioFunds, new ArrayList<>());
        when(fundService.findFunds(commandFunds, fundSession)).thenReturn(portfolioFunds);

        try {
            currentPortfolioCommand.execute(commandFunds, fundSession);
        } catch (FundsOverlapException e) {
            assertEquals("Error: There are no available funds.", e.getMessage());
        }
        assertEquals("Error: There are no available funds.",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));

        verify(fundService).findFunds(commandFunds, fundSession);
    }

}