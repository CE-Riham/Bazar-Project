package org.frontTier;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.common.enums.urls.CategoryUrl;
import org.common.utils.HttpRequestSender;
import org.frontTier.controllers.FrontTierController;
import org.frontTier.services.FrontTierService;

import static spark.Spark.*;
@Slf4j
public class Main {
    private static final Integer PORT = 1218;
    private static final String JSON_TYPE = "application/json";

    public static void main(String[] args) {
        log.info(String.format("Front tier server started on port %d", PORT));
        port(PORT);
        setupControllers();
        setupExceptionHandling();
        after((req, res) ->
                res.type(JSON_TYPE)
        );
    }
    private static void setupControllers() {
        FrontTierService frontTierService = new FrontTierService();

        new FrontTierController(frontTierService);
    }

    private static void setupExceptionHandling() {
        exception(Exception.class, (exception, request, response) -> {
            log.error("Unhandled exception", exception);
            response.status(500);
            response.body("Server error occurred: " + exception.getMessage());
        });
    }
}