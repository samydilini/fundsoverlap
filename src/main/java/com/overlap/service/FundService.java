package com.overlap.service;

import com.overlap.configuration.Configurations;
import com.overlap.exception.FundsMappingException;
import com.overlap.exception.FundsOverlapException;
import com.overlap.mapper.FundsMapper;
import com.overlap.model.Fund;
import com.overlap.model.FundSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This service is used to handle direct fund changes.
 */
public class FundService {
    private final FundsMapper fundsMapper;
    private final Configurations configurations;

    public FundService() {
        this.fundsMapper = new FundsMapper();
        this.configurations = new Configurations();
    }

    /**
     * retrives funds from the get endpoint
     * @return the list of funds retrieved
     * @throws FundsOverlapException
     */
    public List<Fund> get() throws FundsOverlapException {

        try {
            HttpURLConnection connection = configurations.getHttpURLConnection();

            int responseCode = connection.getResponseCode();

            // only process if the code is 200
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return fundsMapper.map(response.toString());
            } else {
                String errorMessage = "Error: GET request not successful.";
                System.out.println(errorMessage);
                throw new FundsOverlapException(errorMessage);
            }

        } catch (IOException | FundsMappingException e) {
            String errorMessage = "Error: Error retrieving available funds. " + e.getMessage();
            System.out.println(errorMessage);
            throw new FundsOverlapException(errorMessage);
        }
    }

    /**
     * Finds commands matching given inputs
     * @param commandFunds funds to be searched
     * @param fundSession session containing current available funds
     * @return funds matching the search
     * @throws FundsOverlapException
     */
    public List<Fund> findFunds(List<String> commandFunds, FundSession fundSession) throws FundsOverlapException {
        if(fundSession.getAvailableFunds().isEmpty()) {
            List<Fund> availableFundsResponse = get();
            if (availableFundsResponse.isEmpty()) {
                String message = "Error: No funds were found in stock data";
                System.out.println(message);
                throw new FundsOverlapException(message);
            }
            fundSession.setAvailableFunds(availableFundsResponse);
        }
        ArrayList<Fund> funds = new ArrayList<>();
        for (String commandFund : commandFunds) {
            Optional<Fund> availableFund = fundSession.getAvailableFunds().stream().filter(fund -> fund.getName().equals(commandFund)).findAny();

            if(!availableFund.isPresent()) {
                return new ArrayList<>();
            }
            funds.add(availableFund.get());
        }
        return funds;
    }

}
