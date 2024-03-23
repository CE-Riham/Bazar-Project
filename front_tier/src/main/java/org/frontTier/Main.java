package org.frontTier;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8082);
        get("/hello1", (req, res) -> "Hello im front tier :)");

    }
}