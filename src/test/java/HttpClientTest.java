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

}
