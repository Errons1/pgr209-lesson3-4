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
                        errorHandler(500, clientSocket);
                    }
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleClientSocket(Socket client) throws IOException {
        var message = new HttpMessage(client);

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


        // 500 error test
        if (message.getFirstLine().split(" ")[1].equals("/500")) throw new IOException();

//        If method arrives here, the URL does not exist and return 404 error
        errorHandler(404, client);

    }
    
    private void errorHandler(int errorCode, Socket client) throws IOException {
        String errorMessage;
        Path errorHtmlFile;

        switch (errorCode) {
            case 500 -> {
                errorMessage = "500 INTERNAL SERVER ERROR";
                errorHtmlFile = Path.of("src/main/resources/error500.html");
            }
            default -> {
                errorMessage = "404 FILE NOT FOUND";
                errorHtmlFile = Path.of("src/main/resources/error404.html");
            }
        }

        client.getOutputStream().write((
                        "HTTP/1.1 "+errorMessage+"\r\n" +
                        "Content-Type: text/html; charset=utf-8\r\n" +
                        "Content-Length: " + errorHtmlFile.toFile().length() + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
                ).getBytes(StandardCharsets.UTF_8)
        );

        try (var fileInputStream = new FileInputStream(errorHtmlFile.toFile())) {
            fileInputStream.transferTo(client.getOutputStream());
        }

    }


//    Getters & setters
    public int getPort() {
        return server.getLocalPort();
    }
    public Path getServerRoot() {
        return serverRoot;
    }

}