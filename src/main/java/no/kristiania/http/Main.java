package no.kristiania.http;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        var client = new HttpClient("httpbin.org", 80, "html");

    }

}
