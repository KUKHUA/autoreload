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