package http;

import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public final class ClientStore {

    private ArrayList<HttpExchange> clients = new ArrayList<HttpExchange>();
    private static ClientStore instance = null;

    private ClientStore(){}

    public void addClient(HttpExchange t) {
        clients.add(t);
    }

    public ArrayList<HttpExchange> clients() {
        return clients;
    }

    public static ClientStore get(){
        if(instance == null) instance = new ClientStore();
        return instance;
    }
}
