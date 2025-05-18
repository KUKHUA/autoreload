package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class SSEConnect implements HttpHandler {
    public void handle(HttpExchange t){
        try {
            t.getResponseHeaders().set("Content-Type", "text/event-stream");
            t.getResponseHeaders().set("Connection", "keep-alive");
            t.getResponseHeaders().set("Cache-Control", "no-cache");
            t.sendResponseHeaders(200, 0);
            t.getResponseBody().write("data: conntected\n\n".getBytes());
            t.getResponseBody().flush();
            ClientStore.get().addClient(t);
            System.out.println("New client connected.");
        } catch(IOException e ){
            System.out.println("Client IO failure. Maybe client crashed?" + e.getMessage());
        }
    }

}