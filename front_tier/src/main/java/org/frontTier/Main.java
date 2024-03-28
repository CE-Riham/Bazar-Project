package org.frontTier;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.common.enums.urls.CategoryUrl;

import static spark.Spark.*;

public class Main {
    private static final Integer PORT = 1218;
    private static final Gson gson = new Gson();
    private static final String JSON_TYPE = "application/json";
    public static void main(String[] args) {

        port(PORT);
        after((req, res) ->
                res.type(JSON_TYPE)
        );
        get("/hello", (req, res) -> {
            //TODO: update path when testing on docker
            String catalogServiceUrl = CategoryUrl.CATALOG_SERVICE_BASE.getUrl();
            System.out.println(catalogServiceUrl);
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(catalogServiceUrl + CategoryUrl.CATEGORY_API_PATH.getUrl() + "/distributed-systems");

                String response = client.execute(request, httpResponse ->
                        EntityUtils.toString(httpResponse.getEntity()));
                System.out.println(response);
                return response;
            } catch (Exception e) {
                return e.toString();
            }
        }, gson::toJson);

    }
}