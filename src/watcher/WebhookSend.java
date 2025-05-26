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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;
import misc.GetEventJSON;
import java.nio.file.Path;
import misc.FileFilter;
import misc.Config;
import org.json.JSONObject;
import org.json.JSONArray;


public final class WebhookSend implements IWatchCallback {
    public void onEvent(String changeType, String fullPath, Path path) {
        if(!FileFilter.isAllowed(path)) return;

        JSONObject object = GetEventJSON.run(changeType, fullPath, path);
        
        Config config = Config.instance();
        String inputArrayString = config.get("sources.webhook.urls");
        String[] webHookList = Arrays.stream(inputArrayString.split(","))
            .map(String::trim)
            .filter(string -> !string.equalsIgnoreCase("null"))
            .toArray(String[]::new);

        for (String webhook : webHookList) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(webhook);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = object.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    System.out.println("Webhook POST successful to: " + webhook + " (HTTP " + responseCode + ")");
                } else {
                    System.out.println("Webhook POST failed to: " + webhook + " (HTTP " + responseCode + ")");
                }

            } catch (Exception e) {
                System.out.println("Unable to send data to webhook " + webhook + ". Error: " + e);
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                } 
            }
        }
    }
}