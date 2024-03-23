package org.order;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8081);
        get("/hello3", (req, res) -> "Hello im Order");

    }
}