package com.overlap.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.overlap.exception.FundsMappingException;
import com.overlap.model.Fund;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapping Json values to a list of funds
 */
public class FundsMapper {

    public List<Fund> map(String response) throws IOException, FundsMappingException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Fund> fundsList = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode fundsNode = rootNode.get("funds");
            if (fundsNode != null && fundsNode.isArray()) {
                for (JsonNode fundNode : fundsNode) {
                    String name = fundNode.get("name").asText();
                    Fund fund = new Fund(name);

                    JsonNode stocksNode = fundNode.get("stocks");
                    List<String> stocks = new ArrayList<>();
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
            String errorMessage = "Exception when Mapping.";
            System.out.println(errorMessage + e.getMessage());
            throw new FundsMappingException(errorMessage);
        }
        return fundsList;

    }
}
