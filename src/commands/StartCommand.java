package commands;
import Command.IHandler;
import Command.Command;
import config.Config;
import http.http;
import http.SSEConnect;
import watcher.SSESend;
import watcher.FolderWatcher;

public class StartCommand implements IHandler {
    @Override
    public void handleCommand(Command command) throws Exception {
        Config config = Config.instance();
        boolean sseEnabled = config.getDefault("sources.sse.enabled", "true") == "true";
        
        if(sseEnabled){
            String port = config.getDefault("sources.sse.port", "1234"); 
            http server = new http(Integer.parseInt(port));
            server.start();
            server.addHandler("/", new SSEConnect());
            System.out.println("Started SSE Server on port " + port + ".");
        }





        FolderWatcher watcher = new FolderWatcher(".");
        if(sseEnabled) watcher.startWatching(new SSESend());
    }

    @Override
    public String getHelpInfo(){
        return "Actually starts looking for changes and broadcast them.";
    }
}