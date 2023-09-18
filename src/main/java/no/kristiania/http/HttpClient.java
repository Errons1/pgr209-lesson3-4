package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class HttpClient {
    private final HttpMessage message;

    public HttpClient(String host, int port, String requestTarget, String cookie) throws IOException {
        Socket client = new Socket(host, port);

        if (cookie.equals("NOR") || cookie.equals("ENG")) {
            client.getOutputStream().write(MessageFormat.format(
                    """
                            GET /{0} HTTP/1.1\r
                            Connection: close\r
                            Cookie: language={1}\r
                            Host: {2}\r
                            \r
                            """, requestTarget, cookie, host).getBytes(StandardCharsets.UTF_8)
            );

        } else {
            client.getOutputStream().write(MessageFormat.format(
                    """
                            GET /{0} HTTP/1.1\r
                            Connection: close\r
                            Host: {1}\r
                            \r
                            """, requestTarget, host).getBytes(StandardCharsets.UTF_8)
            );
        }

        message = new HttpMessage(client);
    }

    public int getSockedCode() {
        String firstLine = message.getFirstLine();
        String code = firstLine.split(" ")[1];
        return Integer.parseInt(code);
    }

    public String getHeader(String fieldName) {
        return message.getHeaders().get(fieldName);
    }

    public String getBody() {
        return message.getRespondBody();
    }

}