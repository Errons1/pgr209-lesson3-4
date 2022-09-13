import no.kristiania.http.HttpClient;
import org.junit.jupiter.api.Test;

public class HttpClientTest {

    @Test
    void shouldReadStatusCode() {
        var client = new HttpClient("httpbin.org", 80 ,"/httpn");
    }
}
