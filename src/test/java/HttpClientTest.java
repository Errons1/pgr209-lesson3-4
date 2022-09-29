import no.kristiania.http.HttpClient;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {

    @Test
    void shouldReadStatusCode200() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "html");
        assertEquals(200, client.getSockedCode());
    }

    @Test
    void shouldReadStatusCode404() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "randomethingsalskdjlaskdj");
        assertEquals(404, client.getSockedCode());
    }

    @Test
    void shouldReadHeaders() throws IOException {
        var client = new HttpClient("httpbin.org", 80, "html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
        assertEquals("3741", client.getHeader("Content-Length"));
    }

}
