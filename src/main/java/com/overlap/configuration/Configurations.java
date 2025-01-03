package com.overlap.configuration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Connection configurations.
 * This class handles the configuration for HTTP connections.
 * The URL can be moved to an environment or properties file for better management.
 */
public class Configurations {
    /**
     * The URL for the stock data.
     */
    public final String URL = "https://geektrust.s3.ap-southeast-1.amazonaws.com/portfolio-overlap/stock_data.json";

    /**
     * Creates and returns an HttpURLConnection for the specified URL.
     *
     * @return an HttpURLConnection object
     * @throws IOException if an I/O exception occurs
     */
    public HttpURLConnection getHttpURLConnection()  throws IOException {
        java.net.URL endpoint = new URL(URL);

        // Open a connection
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
}
