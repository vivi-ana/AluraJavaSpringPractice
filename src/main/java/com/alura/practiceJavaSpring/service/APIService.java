package com.alura.practiceJavaSpring.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Service for handling API requests.
 */
public class APIService {

    /**
     * Fetches data from the specified URL.
     *
     * @param url The URL to fetch data from.
     * @return The fetched data as a String.
     */
    public String fetchData(String url){
        HttpResponse<String> response = this.sendRequest(url);
        return this.handleResponse(response);
    }

    /**
     * Sends an HTTP request and returns the response.
     *
     * @param address The address to send the request to.
     * @return The HTTP response.
     */
    public HttpResponse<String> sendRequest(String address) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Error sending the HTTP request.", e);
        }
    }

    /**
     * Handles the HTTP response.
     *
     * @param response The HTTP response to handle.
     * @return The body of the response.
     */
    private String handleResponse(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error in the API request. Status code: " + response.statusCode());
        }
        return response.body();
    }

}
