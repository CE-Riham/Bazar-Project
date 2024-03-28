package org.order;

import lombok.extern.slf4j.Slf4j;
import org.common.enums.Ports;
import org.order.controllers.OrderController;
import org.order.services.OrderService;

import static spark.Spark.*;

/**
 * This is the main class for the Order Service.
 * It sets up the server, controllers, and exception handling.
 */
@Slf4j
public class Main {
    /**
     * The port number on which the server will run.
     * It is retrieved from the Ports enum.
     */
    private static final Integer PORT = Integer.parseInt(Ports.ORDER_SERVICE_PORT.getPort());

    /**
     * The type of the response that will be returned by the server.
     */
    private static final String JSON_TYPE = "application/json";

    /**
     * The main method of the application.
     * It starts the server, sets up the controllers and exception handling, and sets the response type.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        log.info(String.format("Order server started on port %d", PORT));
        port(PORT);
        setupControllers();
        setupExceptionHandling();
        after((req, res) ->
                res.type(JSON_TYPE)
        );
    }

    /**
     * This method sets up the controllers for the application.
     * It creates an instance of the OrderService and an instance of the OrderController with the OrderService as a parameter.
     */
    private static void setupControllers() {
        OrderService categoryService = new OrderService();
        new OrderController(categoryService);
    }

    /**
     * This method sets up the exception handling for the application.
     * It logs any unhandled exceptions and returns a 500 status code with a message.
     */
    private static void setupExceptionHandling() {
        exception(Exception.class, (exception, request, response) -> {
            log.error("Unhandled exception" + exception);
            response.status(500); // HTTP 500 Internal Server Error
            response.body("Server error occurred: " + exception.getMessage());
        });
    }
}