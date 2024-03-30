package org.frontTier;

import com.google.gson.Gson;
import org.common.enums.urls.CategoryUrl;
import org.common.utils.HttpRequestSender;

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
            String url = catalogServiceUrl + CategoryUrl.CATEGORY_API_PATH.getUrl() + "/1";
//            return HttpRequestSender.sendGetRequest(url);
                return "";
        });
        post("/category", (req, res) -> {
            String catalogServiceUrl = CategoryUrl.CATALOG_SERVICE_BASE.getUrl();
            String url = catalogServiceUrl + CategoryUrl.CATEGORY_ADMIN_API_PATH.getUrl();
            return HttpRequestSender.sendPostRequest(url, req.body());
        });


    }
}