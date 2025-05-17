import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.util.HashMap;
import java.util.Map;
import java.io.UncheckedIOException;


class FolderWatcher {
    private WatchService watcherService;
    private final Map<WatchKey, Path> keys = new HashMap<>();

    public FolderWatcher(String folderPath) {
        try {
            watcherService = FileSystems.getDefault().newWatchService();
            this.registerAll(Paths.get(folderPath));

        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize folder watcher", e);
        }
    }

    private void registerAll(final Path start) throws IOException {
        // i hate files.walk
        Files.walk(start)
            .filter(Files::isDirectory)
            .forEach(path -> {
                try {
                    WatchKey key = path.register(
                        watcherService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY
                    );

                    this.keys.put(key, path);
                } catch(IOException e) {
                    throw new UncheckedIOException("shit",e);
                }
            });
    }

    public void startWatching(IWatchCallback callback){
        Thread watcherThread = new Thread(() -> {
            try {
                while (true) {
                    
                }
            } catch (ClosedWatchServiceException e) {
               throw new RuntimeException("Folder watcher service closed", e);
            }
        });
        watcherThread.setDaemon(true);
        watcherThread.start();
    }


}