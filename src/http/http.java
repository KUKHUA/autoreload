package http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class http {

    int port;
    HttpServer server;

    public http(int port) {
        this.port = port;
    }

    public void start() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandler(String path, HttpHandler handler) {
        this.server.createContext(path, handler);
    }
}
