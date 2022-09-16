package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {

    public Socket socket;

/*      host = Ipaddress of server-client
*       port = 80 for http and 443 https
*       requestTarget = /*type* example: /html
* */
    public HttpClient (String host, int port, String requestTarget) throws IOException{
        socket = new Socket(host, port);

//        sett in get request fra clientside
        socket.getOutputStream().write(
                ("GET /" + requestTarget + " HTTP/1.1\r\n" +
                "Connection: close\r\n" +
                "Host: " + host + "\r\n" +
                "\r\n").getBytes()
        );

        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char)c);
        }
    }

    public int getSockedCode() throws IOException {
        var builder = new StringBuilder();
        int c = 0;

        while ((c = socket.getInputStream().read()) != '\r') {
            builder.append((char) c);
        }

        String string = String.valueOf(builder.toString().split(" ")[1]);
        return Integer.parseInt(string);
    }


}
