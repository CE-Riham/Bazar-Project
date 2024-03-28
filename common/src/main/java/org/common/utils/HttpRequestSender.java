package org.common.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.common.enums.StatusResponse;

public class HttpRequestSender {

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

    public static String sendPostRequest(String url, String json) {
        try (CloseableHttpClient client = HttpClients.createDefault()){
            HttpPost request = new HttpPost(url);
            HttpEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(stringEntity);
            return client.execute(request, httpResponse -> EntityUtils.toString(httpResponse.getEntity()));

        }catch (Exception e) {
            ApiResponse apiResponse = new ApiResponse(StatusResponse.ERROR);
            apiResponse.setMessage(e.getMessage());
            return new Gson().toJson(apiResponse);
        }
    }
}