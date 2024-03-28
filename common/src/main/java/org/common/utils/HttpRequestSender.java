package org.common.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.common.enums.StatusResponse;

/**
 * This class provides utility methods for sending HTTP requests.
 * It includes methods for sending GET and POST requests.
 */
public class HttpRequestSender {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private HttpRequestSender() {
        throw new IllegalStateException("HttpRequestSender class");
    }

    /**
     * Sends a GET request to the specified URL and returns the response body as a string.
     * If an exception occurs during the request, it returns a JSON string with an error message.
     * @param url The URL to send the GET request to.
     * @return The response body as a string.
     */
    public static String sendGetRequest(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            // Execute the request and return the response body
            return client.execute(request, httpResponse -> EntityUtils.toString(httpResponse.getEntity()));
        } catch (Exception e) {
            ApiResponse apiResponse = new ApiResponse(StatusResponse.ERROR);
            apiResponse.setMessage(e.getMessage());
            return new Gson().toJson(apiResponse);
        }
    }

    /**
     * Sends a POST request to the specified URL with the provided JSON string as the request body.
     * Returns the response body as a string.
     * If an exception occurs during the request, it returns a JSON string with an error message.
     * @param url The URL to send the POST request to.
     * @param json The JSON string to include in the request body.
     * @return The response body as a string.
     */
    public static String sendPostRequest(String url, String json) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            HttpEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(stringEntity);
            return client.execute(request, httpResponse -> EntityUtils.toString(httpResponse.getEntity()));

        } catch (Exception e) {
            ApiResponse apiResponse = new ApiResponse(StatusResponse.ERROR);
            apiResponse.setMessage(e.getMessage());
            return new Gson().toJson(apiResponse);
        }
    }
}