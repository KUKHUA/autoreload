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
import org.json.JSONArray;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;
import misc.Config;
import misc.ParseChange;

public final class WebhookSend implements IWatchCallback {
    public void onEvent(String changeType, String fullPath) {
        JSONObject object = new JSONObject();
        String username = "ChangeDetector";
        String headerTitle = "📁 File System Change Detected";
        String normalChangeType = ParseChange.parse(changeType);
        String eventText = String.format("A file/folder has been %s: \n%s",normalChangeType, fullPath);

        object.put("content", eventText);
        object.put("text", eventText);
        object.put("event", eventText);

        object.put("source", username);
        object.put("username", username);

        JSONArray embeds = new JSONArray();
        JSONObject discordEmbed = new JSONObject();
        discordEmbed.put("title", headerTitle);

        discordEmbed.put("description", String.format("**Type**:%s\n**Path**:%s",changeType,fullPath));

        discordEmbed.put("color", 16776960);
        embeds.put(discordEmbed);

        object.put("embeds", embeds);


        JSONObject data = new JSONObject();
        data.put("fullPath", fullPath);
        data.put("changeType", changeType);
        data.put("normalChangeType",normalChangeType);
        object.put("data", data);

        JSONArray blocks = new JSONArray();

        JSONObject headerBlock = new JSONObject();
        headerBlock.put("type", "header");
        headerBlock.put("text", new JSONObject()
        .put("type", "plain_text")
        .put("text", headerTitle)); 

        JSONObject sectionBlock = new JSONObject();
        sectionBlock.put("type", "section");

        JSONArray fields = new JSONArray();
        fields.put(new JSONObject().put("type", "mrkdwn").put("text", "*Type:*\n" + normalChangeType));
        fields.put(new JSONObject().put("type", "mrkdwn").put("text", "*Path:*\n" + fullPath));
        sectionBlock.put("fields", fields);

        JSONObject contextBlock = new JSONObject();
        contextBlock.put("type", "context");
        contextBlock.put("elements", new JSONArray().put(
        new JSONObject().put("type", "mrkdwn").put("text", "Event sent by *ChangeDetector*")));


        blocks.put(headerBlock);
        blocks.put(sectionBlock);
        blocks.put(contextBlock);

        object.put("blocks", blocks);

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


        System.out.println(eventText); 
    }
}