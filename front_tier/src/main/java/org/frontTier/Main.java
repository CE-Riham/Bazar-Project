package org.frontTier;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(1218);
        get("/hello", (req, res) -> {
            String orderServiceUrl = "http://order_service:8082/hello";

            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(orderServiceUrl);

                String response = client.execute(request, httpResponse ->
                        EntityUtils.toString(httpResponse.getEntity()));

                return ("Response from order_service: " + response);
            } catch (Exception e) {
                return e.toString();
            }
        });

    }
}