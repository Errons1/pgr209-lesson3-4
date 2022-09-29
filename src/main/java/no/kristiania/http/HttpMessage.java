package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
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
    String firstLine;
    Map<String, String> headers;
    String respondBody;

    public HttpMessage(Socket client) throws IOException {
        firstLine = readLine(client);
        headers = readHeaders(client);
//        respondBody = readBody(client);
    }

    private String readLine(Socket client) throws IOException {
        var builder = new StringBuilder();
        int c = 0;

//        Reads til end of first line ==  "... \r\n"
        while((c = client.getInputStream().read()) != '\r') {
            builder.append((char)c);
        }
        c = client.getInputStream().read(); //read the next \n

        return builder.toString();
    }

    private Map<String, String> readHeaders(Socket client) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;

//        When readLine meets only \r\n it returns an empty string
        while(!(line = readLine(client)).isEmpty()) {
            var header= line.split(": ");
            headers.put(header[0], header[1]);
        }

        return headers;
    }

    private String readBody(Socket client) {

        return null;
    }


//    Getters & setters
    public String getFirstLine() {
        return firstLine;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }
    public String getRespondBody() {
        return respondBody;
    }


}
