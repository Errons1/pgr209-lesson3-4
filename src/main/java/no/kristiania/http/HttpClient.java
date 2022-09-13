package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {

    public HttpClient(String ip4, int port, String request) {
    }

    public static void main(String[] args) throws IOException {
        var socket = new Socket("httpbin.org", 80);

//        sett in get request fra clientside
        socket.getOutputStream().write(
                ("GET /html HTTP/1.1\r\n " +
                 "Connection: keep alive\r\n" +
                 "Host: httpbin.org\r\n" +
                 "\r\n").getBytes());


        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char)c);
        }

    }

}
