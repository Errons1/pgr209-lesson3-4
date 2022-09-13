package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {

    public Socket socket;

    public HttpClient (String host, int port, String requestTarget) throws IOException{
        socket = new Socket(host, port);

//        sett in get request fra clientside
        socket.getOutputStream().write(
                ("GET /" + requestTarget + " HTTP/1.1\r\n" +
                "Connection: close\r\n" +
                "Host: " + host + "\r\n" +
                "\r\n").getBytes()
        );

        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char)c);
        }
    }

    public int getSockedCode() {
        String socetCode = socket.toString();
        return 200;
    }


}
