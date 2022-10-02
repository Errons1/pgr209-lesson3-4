import no.kristiania.http.HttpClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HttpServerTest {

    @Test
    void serverRespond404() throws IOException {
        var server = new HttpServer(0, Path.of("src/test/resources/index.html"));
        var client = new HttpClient("localhost", server.getPort(), "random");

        assertEquals(404, client.getSockedCode());
    }

    @Test
    void shouldRespondWith200ForKnownUrl() throws IOException {
        var server = new HttpServer(0, Path.of("src/test/resources/index.html"));
        var client = new HttpClient("localhost", server.getPort(), "");

        assertEquals(200, client.getSockedCode());
        assertEquals("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                <meta charset="UTF-8">
                <title>Index</title>
                </head>
                <body>
                <h1>Hi I am the Index page!</h1>
                <p>Dette er Norske bokstaver for test ø æ å.</p>
                </body>
                </html>""", client.getBody());
    }

    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        var server = new HttpServer(0, Path.of("src/test/resources/index.html"));
        var client0 = new HttpClient("localhost", server.getPort(), "");
        var client1 = new HttpClient("localhost", server.getPort(), "");

        assertEquals(200, client0.getSockedCode());
        assertEquals(200, client1.getSockedCode());
    }

    @Test
    void shouldReturn500OnUnhandledErrors() throws IOException {
        var server = new HttpServer(0, Path.of("src/test/resources/index.html"));
        var client = new HttpClient("localhost", server.getPort(), "500");
        assertEquals(500, client.getSockedCode());
    }
}















