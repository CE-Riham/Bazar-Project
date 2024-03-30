package org.common.utils;

import lombok.extern.java.Log;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.common.enums.StatusCode;

/**
 * This class is used to send HTTP requests.
 * It provides methods to send GET and POST requests.
 */
@Log
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
    public static String sendGetRequest(String url, spark.Response res) {
        log.info("sending a GET request to " + url);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            // Execute the request and process the response
            return client.execute(request, httpResponse -> {
                // Here you can check httpResponse status code and decide what to do
                res.status(httpResponse.getStatusLine().getStatusCode());
                return EntityUtils.toString(httpResponse.getEntity());
            });
        } catch (Exception e) {
            res.status(StatusCode.INTERNAL_SERVER_ERROR.getCode());
            return "Can't send GET request: " + e.getMessage();
        }
    }

    /**
     * Sends a POST request to the specified URL with the provided JSON body and returns the response body.
     *
     * @param url  The URL to send the POST request to.
     * @param json The JSON body to include in the POST request.
     * @return The response body as a string, or a MessageResponse object if an exception occurred.
     */
    public static Object sendPostRequest(String url, String json, spark.Response res) {
        log.info("sending a POST request to " + url);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            HttpEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(stringEntity);
            // Execute the request and process the response
            return client.execute(request, httpResponse -> {
                res.status(httpResponse.getStatusLine().getStatusCode());
                return EntityUtils.toString(httpResponse.getEntity());
            });
        } catch (Exception e) {
            res.status(StatusCode.INTERNAL_SERVER_ERROR.getCode());
            return "Can't send POST request: " + e.getMessage();
        }
    }

    public static String sendPutRequest(String url, String json, spark.Response res) {
        log.info("sending a PUT request to " + url);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut request = new HttpPut(url);
            HttpEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(stringEntity);

            // Execute the request and process the response
            return client.execute(request, httpResponse -> {
                res.status(httpResponse.getStatusLine().getStatusCode());
                return EntityUtils.toString(httpResponse.getEntity());
            });
        } catch (Exception e) {
            res.status(StatusCode.INTERNAL_SERVER_ERROR.getCode());
            return "Can't send PUT request: " + e.getMessage();
        }
    }
}