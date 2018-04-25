package guru.mrtu;

import static spark.Spark.get;

public class Application {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }

}
