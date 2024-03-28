package org.common.utils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpRequestSender {


//    public ApiResponse sendGetRequest(String url) throws InterruptedException, IOException {
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet(url);
//
//            String response = client.execute(request, httpResponse ->
//                    EntityUtils.toString(httpResponse.getEntity()));
//
//            return ("Response from order_service: " + response);
//        } catch (Exception e) {
//            return e.toString();
//        }
//    }
}
