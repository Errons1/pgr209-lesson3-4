import no.kristiania.http.HttpClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HttpServerTest {

    @Test
    void serverRespond404() throws IOException {
        var server = new HttpServer(9080);
        var client = new HttpClient("localhost", server.getPort(), "random");

        assertEquals(404, client.getSockedCode());
    }

    @Test
    void shouldRespondWith200ForKnownUrl() throws IOException {

    }

    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {

    }

    @Test
    void shouldReturn500OnUnhandledErrors() throws IOException {

    }
}
