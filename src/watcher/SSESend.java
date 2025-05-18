package watcher;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import http.ClientStore; 



public final class SSESend implements IWatchCallback {
    public void onEvent(String changeType, String fullPath) {
        String changedText = "FILE: " + fullPath + " " + changeType;
        System.out.println(changedText);

        for (HttpExchange client : ClientStore.get().clients()) {
            try {
                client.getResponseBody().write(("data:" + changedText + "\n\n").getBytes());

                client.getResponseBody().flush();

            } catch (IOException e) {
                System.out.println("Failed to notify client: " + e.getMessage());
                ClientStore.get().clients().remove(client);
            }
        }
    }
}