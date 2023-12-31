package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/*
 *   Respond metadata is in ascii characters
 *   -Makes it safe to read like this: (Char)byte[]
 *
 *   Respond body is in UTF-8 characters
 *   -Need to be read like this String(byte[], UTF-8)
 *
 * */

public class HttpMessage {
    private final String firstLine;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private int contentLength = 0;
    private final String respondBody;

    public HttpMessage(Socket client) throws IOException {
        firstLine = readLine(client);
        headers = readHeaders(client);
        cookies = readCookies();
        if (headers.containsKey("Content-Length")) {
            contentLength = readContentLength();
        }
        respondBody = readBody(client);
    }

    private String readLine(Socket client) throws IOException {
        var builder = new StringBuilder();
        int c;

//        Reads til end of first line ==  "... \r\n"
        while ((c = client.getInputStream().read()) != '\r') {
            if (c == -1) break;

            builder.append((char) c);
        }
        client.getInputStream().read(); //read the next \n

        return builder.toString();
    }

    private Map<String, String> readHeaders(Socket client) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;

//        When readLine meets only \r\n it returns an empty string
        while (!(line = readLine(client)).isEmpty()) {
            var header = line.split(": ");
            headers.put(header[0], header[1]);
        }

        return headers;
    }

    private Map<String, String> readCookies() {
        String cookieLine = getHeaders().get("Cookie");
        if (cookieLine == null) {
            return null;
        }

        String[] cookieArray = cookieLine.split("\\s*;\\s*|\\s*=\\s*");
        HashMap<String, String> cookies = new HashMap<>();

//        index starts at 1 for not doing a index out of bound error
        for (int i = 1; i < cookieArray.length; i+= 2) {
            cookies.put(cookieArray[i - 1], cookieArray[i]);
        }

        return cookies;
    }

    private int readContentLength() {
        String length = headers.get("Content-Length");
        return Integer.parseInt(length);
    }

    private String readBody(Socket client) throws IOException {
        var body = new byte[contentLength];
        for (int i = 0; i < body.length; i++) {
            body[i] = (byte) client.getInputStream().read();
        }
        return new String(body, StandardCharsets.UTF_8);
    }




    //    Getters & setters
    public String getFirstLine() {
        return firstLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getRespondBody() {
        return respondBody;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
