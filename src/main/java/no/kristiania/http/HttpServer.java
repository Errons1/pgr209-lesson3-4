package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {

    ServerSocket server;

    public HttpServer(int port) throws IOException {
        server = new ServerSocket(port);
    }

    public int getPort() {
        return server.getLocalPort();
    }

}
