package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class HttpServer {

    ServerSocket server;

    public HttpServer(int port) throws IOException {
        server = new ServerSocket(port);
        start();
    }

    private void start() {
//        Makes new thread for taking the server run
        new Thread(() -> {
            while (true) {
                try {
                    var clientSocket = server.accept();
                    handleClientSocket(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleClientSocket(Socket client) throws IOException {
        var message = new HttpMessage(client);

        client.getOutputStream().write(
                        """
                        HTTP/1.1 404 NOT FOUND\r
                        Content-Type: text/html\r
                        Content-Length: 233\r
                        Connection: close\r
                        \r
                        <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
                        <title>404 Not Found</title>
                        <h1>Not Found</h1>
                        <p>The requested URL was not found on the server.  If you entered the URL manually please check your spelling and try again.</p>
                        """.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String respondHeaderMetaData(int code, int length, String body) {
        String headerText = MessageFormat.format("""
                HTTP/1.1 {0}\r
                Content-Type: text/html\r
                Content-Length: {1}\r
                Connection: close\r
                \r
                
                """, ServerCode(code));
        return headerText;
    }

    private String ServerCode(int code) {
        return switch(code) {
            case 200 -> "200 OK";
            default -> "404 NOT FOUND";
        };
    }

    public int getPort() {
        return server.getLocalPort();
    }

}
