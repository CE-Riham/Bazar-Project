package org.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * This class is used to send HTTP requests.
 * It provides methods to send GET and POST requests.
 */
public class HttpRequestSender {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private HttpRequestSender() {
        throw new IllegalStateException("HttpRequestSender class");
    }

    /**
     * Sends a GET request to the specified URL and returns the response body.
     *
     * @param url The URL to send the GET request to.
     * @return The response body as a string, or a MessageResponse object if an exception occurred.
     */
    public static Object sendGetRequest(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            // Execute the request and return the response body
            return client.execute(request, httpResponse -> EntityUtils.toString(httpResponse.getEntity()));
        } catch (Exception e) {
            return new MessageResponse("Can't send GET request: " + e.getMessage());
        }
    }

    /**
     * Sends a POST request to the specified URL with the provided JSON body and returns the response body.
     *
     * @param url  The URL to send the POST request to.
     * @param json The JSON body to include in the POST request.
     * @return The response body as a string, or a MessageResponse object if an exception occurred.
     */
    public static Object sendPostRequest(String url, String json) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            HttpEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(stringEntity);
            return client.execute(request, httpResponse -> EntityUtils.toString(httpResponse.getEntity()));

        } catch (Exception e) {
            return new MessageResponse("Can't send POST request: " + e.getMessage());
        }
    }
}