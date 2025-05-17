import Command.Manager;
import http.http;
import http.SSEConnect;
import http.ClientStore;
import http.SSESend;
import java.io.IOException;
import watcher.FolderWatcher;

public class Main  {
    public static void main(String[] args) {
        http server = new http(1337);
        server.start();
        server.addHandler(
            "/",
            new SSEConnect()
        );

        FolderWatcher watcher = new FolderWatcher(".");
        watcher.startWatching(new SSESend());    

    }
}