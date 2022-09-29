import no.kristiania.http.HttpClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HttpServerTest {

    @Test
    void serverRespond404() throws IOException {
        var server = new HttpServer(0);
        var client = new HttpClient("localhost", server.getPort(), "randome");

        assertEquals(404, client.getSockedCode());
    }


}
