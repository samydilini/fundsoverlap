package com.overlap.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.overlap.exception.FundsMappingException;
import com.overlap.model.Fund;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapping Json values to a list of funds.
 */
public class FundsMapper {

    /**
     * Maps a JSON response to a list of Fund objects.
     *
     * @param response the JSON response as a String
     * @return a list of Fund objects
     * @throws IOException           if an I/O exception occurs
     * @throws FundsMappingException if a mapping exception occurs
     */
    public List<Fund> map(String response) throws IOException, FundsMappingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Fund> fundsList = new ArrayList<>();

        try {
            JsonNode fundsNode = objectMapper.readTree(response).get("funds");
            if (fundsNode != null && fundsNode.isArray()) {
                for (JsonNode fundNode : fundsNode) {
                    Fund fund = new Fund(fundNode.get("name").asText());
                    List<String> stocks = new ArrayList<>();
                    JsonNode stocksNode = fundNode.get("stocks");
                    if (stocksNode != null && stocksNode.isArray()) {
                        for (JsonNode stockNode : stocksNode) {
                            stocks.add(stockNode.asText());
                        }
                    }
                    fund.setStocks(stocks);
                    fundsList.add(fund);
                }
            }
        } catch (Exception e) {
            throw new FundsMappingException("Exception when Mapping.");
        }
        return fundsList;
    }
}
