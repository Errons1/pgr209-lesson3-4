package no.kristiania.http;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
//        One is sending without cookie, the other is sending with cookie values
        var client = new HttpClient("localhost", 8080, "", "");
//        var client = new HttpClient("localhost", 8080, "", "NOR");
//        var client = new HttpClient("localhost", 8080, "", "ENG");
        System.out.println(client.getBody());
    }
}
