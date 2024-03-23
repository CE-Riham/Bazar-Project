package org.order;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8083);
        get("/hello", (req, res) -> "Hello im Order");

    }
}