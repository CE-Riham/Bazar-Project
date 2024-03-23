package org.order;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8082);
        get("/hello", (req, res) -> "Hello from Order Service");

    }
}