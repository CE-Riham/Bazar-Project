package org.order;
import static spark.Spark.*;

public class Main {
    static final private Integer PORT = 8082;

    public static void main(String[] args) {
        port(PORT);
        get("/hello", (req, res) -> "Hello from Order Service");
    }
}