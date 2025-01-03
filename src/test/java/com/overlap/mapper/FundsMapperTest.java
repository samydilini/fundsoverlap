package com.overlap.mapper;

import com.overlap.exception.FundsMappingException;
import com.overlap.model.Fund;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.overlap.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FundsMapperTest {

    FundsMapper fundsMapper = new FundsMapper();


    @Test
    public void shouldSuccessfullyGenerateFundsForWellFormedResponse() throws IOException, FundsMappingException {

        List<Fund> funds = fundsMapper.map(FUNDS_RESPONSE);

        assertEquals(2, funds.size());
        Fund firstFund = funds.get(0);
        assertEquals(FIRST_FUND_NAME, firstFund.getName());
        assertEquals(STOCK1, firstFund.getStocks().get(0));
        assertEquals(STOCK2, firstFund.getStocks().get(1));
        Fund secondFund = funds.get(1);
        assertEquals(SECOND_FUND_NAME, secondFund.getName());
        assertEquals(STOCK1, secondFund.getStocks().get(0));
        assertEquals(STOCK3, secondFund.getStocks().get(1));
    }


    @Test
    public void empty() throws IOException {
        try {
            fundsMapper.map("");
        } catch (FundsMappingException e) {
            assertEquals("Exception when Mapping.", e.getMessage());
        }
    }
}