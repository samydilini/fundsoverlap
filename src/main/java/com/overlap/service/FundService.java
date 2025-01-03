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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This service is used to handle direct fund changes.
 */
public class FundService {
    private final FundsMapper fundsMapper;
    private final Configurations configurations;

    /**
     * Default constructor initializing FundsMapper and Configurations.
     */
    public FundService() {
        this.fundsMapper = new FundsMapper();
        this.configurations = new Configurations();
    }

    /**
     * Constructs a FundService with the provided FundsMapper and Configurations.
     * Used for unit tests.
     *
     * @param fundsMapper    the funds mapper to be used by this service
     * @param configurations the configurations to be used by this service
     */
    public FundService(FundsMapper fundsMapper, Configurations configurations) {
        this.fundsMapper = fundsMapper;
        this.configurations = configurations;
    }

    /**
     * Retrieves funds from the GET endpoint.
     *
     * @return the list of funds retrieved
     * @throws FundsOverlapException if there is an error retrieving the funds
     */
    public List<Fund> get() throws FundsOverlapException {

        try {
            HttpURLConnection connection = configurations.getHttpURLConnection();

            int responseCode = connection.getResponseCode();

            // only process if the code is 200
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = in.lines().collect(Collectors.joining());
                    return fundsMapper.map(response);
                }
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
     * Finds commands matching given inputs.
     *
     * @param commandFunds funds to be searched
     * @param fundSession  session containing current available funds
     * @return funds matching the search
     * @throws FundsOverlapException if there is an error finding the funds
     */
    public List<Fund> findFunds(List<String> commandFunds, FundSession fundSession) throws FundsOverlapException {
        if (fundSession.getAvailableFunds().isEmpty()) {
            List<Fund> availableFundsResponse = get();
            if (availableFundsResponse.isEmpty()) {
                String message = "Error: No funds were found in stock data";
                System.out.println(message);
                throw new FundsOverlapException(message);
            }
            fundSession.setAvailableFunds(availableFundsResponse);
        }

        return commandFunds.stream()
                .map(commandFund -> fundSession.getAvailableFunds().stream()
                        .filter(fund -> fund.getName().equals(commandFund))
                        .findAny())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
