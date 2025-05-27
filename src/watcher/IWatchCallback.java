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

import java.nio.file.Path;

/**
 * Callback interface for handling file system watch events.
 * Implementations of this interface can be used to receive notifications
 * when a file or directory changes.
 * The {@code onEvent} method is called with the type of change, the full path
 * of the file or directory, and the {@link Path} object representing the path.
 */
public interface IWatchCallback {
    void onEvent(String changeType, String fullPath, Path path);
}