package no.kristiania.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class HttpServer {

    private final Path serverRoot;
    ServerSocket server;

    public HttpServer(int port, Path serverRoot) throws IOException {
        server = new ServerSocket(port);
        this.serverRoot = serverRoot;
        start();
    }

    private void start() {
//        Makes new thread for taking the server run
        new Thread(() -> {
            while (true) {
                try {
                    var clientSocket = server.accept();
                    try {
                        handleClientSocket(clientSocket);
                    } catch (Exception e) {
                        error500(clientSocket);
                    }
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void error500(Socket client) throws IOException {
        client.getOutputStream().write(
                """
                HTTP/1.1 500 INTERNAL SERVER ERROR\r
                Content-Type: text/html; charset=utf-8\r
                Connection: close\r
                \r
                """.getBytes(StandardCharsets.UTF_8)
        );
    }

    private void handleClientSocket(Socket client) throws IOException {
        var message = new HttpMessage(client);
        // 500 error test
        if (message.getFirstLine().split(" ")[1].equals("/500")) throw new IOException();

//        if request == GET / HTTP/1.1
        if (message.getFirstLine().split(" ")[1].equals("/")) {
            client.getOutputStream().write((
                    "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html; charset=utf-8\r\n" +
                    "Content-Length: " + serverRoot.toFile().length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n"
                    ).getBytes(StandardCharsets.UTF_8)
            );

            try (var fileInputStream = new FileInputStream(serverRoot.toFile())) {
                fileInputStream.transferTo(client.getOutputStream());
            }

            return;
        }

        client.getOutputStream().write(
                        """
                        HTTP/1.1 404 NOT FOUND\r
                        Content-Type: text/html; charset=utf-8\r
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


//    Getters & setters
    public int getPort() {
        return server.getLocalPort();
    }
    public Path getServerRoot() {
        return serverRoot;
    }

}


//    Though of doing some auto message system based on some code, might come back to this
//    private String respondHeaderMetaData(int code, int length, String body) {
//        String headerText = MessageFormat.format("""
//                HTTP/1.1 {0}\r
//                Content-Type: text/html\r
//                Content-Length: {1}\r
//                Connection: close\r
//                \r
//
//                """, ServerCode(code));
//        return headerText;
//    }
//
//    private String ServerCode(int code) {
//        return switch(code) {
//            case 200 -> "200 OK";
//            default -> "404 NOT FOUND";
//        };
//    }
