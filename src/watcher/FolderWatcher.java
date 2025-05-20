/*
 * ChangeDetector -  detects modifications, deletions, or creations of files and folders.
 * Copyright (C) 2025 KUKHUA
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package watcher;

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


public final class FolderWatcher {
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
                    WatchKey key = watcherService.take();
                    Path dir = keys.get(key);
                    if (dir == null) continue;

                    for(WatchEvent<?> event : key.pollEvents()){
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == StandardWatchEventKinds.OVERFLOW) continue;

                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path name = ev.context();
                        Path child = dir.resolve(name);
                        String changeType = kind.name();

                        callback.onEvent(changeType, child.toAbsolutePath().toString());

                        // register newly creeated folders. 
                        if (kind == StandardWatchEventKinds.ENTRY_CREATE && Files.isDirectory(child)) {
                            this.registerAll(child);
                        }
                    }

                    if(!key.reset()){
                        keys.remove(key);
                        if(keys.isEmpty()) break;
                    }
                }
            } catch (Exception e) {
               throw new RuntimeException("Folder watcher service closed", e);
            }
        });
        watcherThread.start();
    }

}