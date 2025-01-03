package com.overlap.configuration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Connection configurations
 * can be moved to an env or properties file
 */
public class Configurations {
    public final String URL = "https://geektrust.s3.ap-southeast-1.amazonaws.com/portfolio-overlap/stock_data.json";

    public HttpURLConnection getHttpURLConnection()  throws IOException {
        java.net.URL endpoint = new URL(URL);

        // Open a connection
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
}
