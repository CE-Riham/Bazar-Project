package org.catalog;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8081);
        get("/hello2", (req, res) -> "Hello im catalog");

    }
}