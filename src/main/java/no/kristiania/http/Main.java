package no.kristiania.http;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var server = new HttpServer(8080);
        var test = new HttpClient("127.0.0.1", 8080, "asdasdasd");
        System.out.println(test.getBody());
    }
}
