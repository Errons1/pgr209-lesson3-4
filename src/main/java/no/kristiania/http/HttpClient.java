package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class HttpClient {
    Socket client;
    HttpMessage message;

//    (String.format(
//            "GET /%s HTTP/1.1\r\n" +
//            "Connection: close\r\n" +
//            "Host: %s\r\n",
//    requestTarget, host
//                )

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        client = new Socket(host, port);

        client.getOutputStream().write((
                "GET /" + requestTarget + " HTTP/1.1\r\n" +
                "Connection: close\r\n" +
                "Host: " + host + "\r\n\r\n").getBytes(StandardCharsets.UTF_8)
        );

        message = new HttpMessage(client);
    }

    public int getSockedCode() {
        String firstLine = message.getFirstLine();
        String code = firstLine.split(" ")[1];
        return Integer.parseInt(code);
    }

    public static void main(String[] args) {
        String test = "test";
        String test1 = String.format("This is a %s", test);
        System.out.println(test1);
    }

}