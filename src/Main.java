import Command.Manager;
import http.http;
import http.SSEConnect;
import http.ClientStore;
import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

public class Main  {
    public static void main(String[] args) {
        http server = new http(1337);
        server.start();
        server.addHandler(
            "/",
            new SSEConnect()
        );

        FolderWatcher watcher = new FolderWatcher(".");
        watcher.startWatching(new IWatchCallback() {
            @Override
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
        });     

    }
}