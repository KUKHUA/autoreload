package http;

import watcher.IWatchCallback;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;



public class SSESend implements IWatchCallback {
    public void onEvent() {
        System.out.println("File changed!");

        for (HttpExchange client : ClientStore.get().clients()) {
            try {
                client.getResponseBody().write("data: file changed\n\n".getBytes());
                client.getResponseBody().flush();

            } catch (IOException e) {
                System.out.println("Failed to notify client: " + e.getMessage());
                ClientStore.get().clients().remove(client);
            }
        }
    }

}