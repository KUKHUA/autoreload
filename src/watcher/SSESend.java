package watcher;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import http.ClientStore; 
import org.json.JSONObject;


public final class SSESend implements IWatchCallback {
    public void onEvent(String changeType, String fullPath) {
        JSONObject object = new JSONObject();
        object.put("changedType", changeType);
        object.put("fullPath", fullPath);

        System.out.println(object.toString());

        for (HttpExchange client : ClientStore.get().clients()) {
            try {
                client.getResponseBody().write(("data:" + object.toString() + "\n\n").getBytes());

                client.getResponseBody().flush();

            } catch (IOException e) {
                System.out.println("Failed to notify client: " + e.getMessage());
                ClientStore.get().clients().remove(client);
            }
        }
    }
}