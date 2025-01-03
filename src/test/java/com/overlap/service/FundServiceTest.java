package com.overlap.service;

import com.overlap.configuration.Configurations;
import com.overlap.exception.FundsMappingException;
import com.overlap.exception.FundsOverlapException;
import com.overlap.mapper.FundsMapper;
import com.overlap.model.Fund;
import com.overlap.model.FundSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.overlap.TestConstants.FUNDS_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class FundServiceTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @Mock
    private FundsMapper fundsMapper;
    @Mock
    private Configurations configurations;
    @InjectMocks
    FundService fundService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        outContent.reset();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void returnsFundsIfConnectionIsSuccessful() throws IOException, FundsOverlapException, FundsMappingException {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);

        when(configurations.getHttpURLConnection()).thenReturn(mockConnection);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(FUNDS_RESPONSE.getBytes()));

        List<Fund> mapperFunds = new ArrayList<>();
        Fund fund = new Fund("testFund");
        mapperFunds.add(fund);
        when(fundsMapper.map(anyString())).thenReturn(mapperFunds);

        List<Fund> funds = fundService.get();

        assertEquals(1, funds.size());
        assertSame(mapperFunds, funds);
        verify(fundsMapper).map(FUNDS_RESPONSE.replace("\n", "")
        );
    }

    @Test
    public void throwsErrorIfConnectionIsNotSuccessful() throws IOException {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(configurations.getHttpURLConnection()).thenReturn(mockConnection);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);

        try {
            fundService.get();
        } catch (FundsOverlapException e) {
            assertEquals("Error: GET request not successful.", e.getMessage());
        }
        assertEquals("Error: GET request not successful.",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));

    }

    @Test
    public void throwsErrorIfInputStreamFetchThrowsError() throws IOException {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);

        when(configurations.getHttpURLConnection()).thenReturn(mockConnection);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

        doThrow(new IOException("IO exception")).when(mockConnection).getInputStream();

        try {
            fundService.get();
        } catch (FundsOverlapException e) {
            assertEquals("Error: Error retrieving available funds. IO exception", e.getMessage());
        }
        assertEquals("Error: Error retrieving available funds. IO exception",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));

    }

    @Test
    public void throwsErrorIfMappingIsNotSuccessful() throws IOException, FundsMappingException {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);

        when(configurations.getHttpURLConnection()).thenReturn(mockConnection);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(FUNDS_RESPONSE.getBytes()));

        when(fundsMapper.map(anyString())).thenThrow(new FundsMappingException("Mapping Error"));

        try {
            fundService.get();
        } catch (FundsOverlapException e) {
            assertEquals("Error: Error retrieving available funds. Mapping Error", e.getMessage());
        }
        assertEquals("Error: Error retrieving available funds. Mapping Error",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));

    }


    @Test
    public void findFundIfIsAvailable() throws IOException, FundsOverlapException, FundsMappingException {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);

        when(configurations.getHttpURLConnection()).thenReturn(mockConnection);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(FUNDS_RESPONSE.getBytes()));

        List<Fund> mapperFunds = new ArrayList<>();
        Fund fund1 = new Fund("testFund1");
        fund1.setStocks(Arrays.asList("stock 1", "stock 2"));
        String testFund2 = "testFund2";
        Fund fund2 = new Fund(testFund2);
        fund2.setStocks(Arrays.asList("stock 1", "stock 3"));
        String testFund3 = "testFund3";
        Fund fund3 = new Fund(testFund3);
        fund3.setStocks(Arrays.asList("stock 5", "stock 6"));
        mapperFunds.add(fund1);
        mapperFunds.add(fund2);
        mapperFunds.add(fund3);
        when(fundsMapper.map(anyString())).thenReturn(mapperFunds);
        when(fundsMapper.map(anyString())).thenReturn(mapperFunds);
        FundSession fundSession = new FundSession(new ArrayList<>(), new ArrayList<>());


        List<Fund> funds = fundService.findFunds(Arrays.asList(testFund2, testFund3), fundSession);

        assertEquals(2, funds.size());
        assertEquals(testFund2, funds.get(0).getName());
        assertEquals(testFund3, funds.get(1).getName());
        verify(fundsMapper).map(FUNDS_RESPONSE.replace("\n", "")
        );
    }

    @Test
    public void ErrorIfFundIsNotAvailable() throws IOException, FundsMappingException {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);

        when(configurations.getHttpURLConnection()).thenReturn(mockConnection);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(FUNDS_RESPONSE.getBytes()));
        List<Fund> mapperFunds = new ArrayList<>();
        String testFund2 = "testFund2";
        String testFund3 = "testFund3";
        when(fundsMapper.map(anyString())).thenReturn(mapperFunds);
        when(fundsMapper.map(anyString())).thenReturn(mapperFunds);
        FundSession fundSession = new FundSession(new ArrayList<>(), new ArrayList<>());


        try {
            fundService.findFunds(Arrays.asList(testFund2, testFund3), fundSession);
        } catch (FundsOverlapException e) {
            assertEquals("Error: No funds were found in stock data",
                    outContent.toString().replace("\n", "")
                            .replace("\r", ""));
            assertEquals("Error: No funds were found in stock data",
                    e.getMessage());
        }
        verify(fundsMapper).map(FUNDS_RESPONSE.replace("\n", "")
        );
    }

    @Test
    public void exceptionIfInoutCommandsNotMatch() throws IOException, FundsMappingException {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);

        when(configurations.getHttpURLConnection()).thenReturn(mockConnection);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(FUNDS_RESPONSE.getBytes()));

        List<Fund> mapperFunds = new ArrayList<>();
        Fund fund1 = new Fund("testFund1");
        fund1.setStocks(Arrays.asList("stock 1", "stock 2"));
        String testFund2 = "testFund2";
        Fund fund2 = new Fund(testFund2);
        fund2.setStocks(Arrays.asList("stock 1", "stock 3"));
        String testFund3 = "testFund3";
        Fund fund3 = new Fund(testFund3);
        fund3.setStocks(Arrays.asList("stock 5", "stock 6"));
        mapperFunds.add(fund1);
        mapperFunds.add(fund2);
        mapperFunds.add(fund3);
        when(fundsMapper.map(anyString())).thenReturn(mapperFunds);
        FundSession fundSession = new FundSession(new ArrayList<>(), new ArrayList<>());

        try {
            fundService.findFunds(Arrays.asList("wrong fund1", "wrong fund2"), fundSession);
        } catch (FundsOverlapException e) {
            assertEquals("Error: No matching funds were found from the input commands.",
                    outContent.toString().replace("\n", "")
                            .replace("\r", ""));
            assertEquals("Error: No matching funds were found from the input commands.",
                    e.getMessage());
        }
        verify(fundsMapper).map(FUNDS_RESPONSE.replace("\n", "")
        );
    }

    @Test
    public void findFundFromSessionIfAvailable() throws FundsOverlapException {
        List<Fund> availableFunds = new ArrayList<>();
        Fund fund1 = new Fund("testFund1");
        fund1.setStocks(Arrays.asList("stock 1", "stock 2"));
        String testFund2 = "testFund2";
        Fund fund2 = new Fund(testFund2);
        fund2.setStocks(Arrays.asList("stock 1", "stock 3"));
        String testFund3 = "testFund3";
        Fund fund3 = new Fund(testFund3);
        fund3.setStocks(Arrays.asList("stock 5", "stock 6"));
        availableFunds.add(fund1);
        availableFunds.add(fund2);
        availableFunds.add(fund3);
        FundSession fundSession = new FundSession(new ArrayList<>(), availableFunds);

        List<Fund> funds = fundService.findFunds(Arrays.asList(testFund2, testFund3), fundSession);

        assertEquals(2, funds.size());
        assertEquals(testFund2, funds.get(0).getName());
        assertEquals(testFund3, funds.get(1).getName());
        verifyZeroInteractions(fundsMapper);
        verifyZeroInteractions(configurations);
    }
}