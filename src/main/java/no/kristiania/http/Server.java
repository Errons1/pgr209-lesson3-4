package no.kristiania.http;

import java.io.IOException;
import java.nio.file.Path;

public class Server {
    public static void main(String[] args) throws IOException {
        var server = new HttpServer(8080, Path.of("src/main/resources/index.html"));
    }
}

//        var server = new HttpServer(8080, Path.of("src/main/resources/index.html"));
//        var client = new HttpClient("localhost", 8080, "");
//        System.out.println(client.getBody());