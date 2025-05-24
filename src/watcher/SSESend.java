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
import java.nio.file.Path;
import misc.ParseChange;
import misc.FileFilter;


public final class SSESend implements IWatchCallback {
    public void onEvent(String changeType, String fullPath, Path path) {
        if(!FileFilter.isAllowed(path)) return;

 if(!FileFilter.isAllowed(path)) return;

        try {
            fullPath = path.toRealPath().toString();
        } catch(Exception e){}

        JSONObject object = new JSONObject(); 
        String username = "ChangeDetector";
        String normalChangeType = ParseChange.parse(changeType);
        String typeOfObject = FileFilter.objectType(path);
        String headerTitle = "📁 Filesystem " + ParseChange.parseion(changeType) + " detected."; 
        String eventText = String.format("A %s has been %s: \n%s",typeOfObject, normalChangeType, fullPath);

        object.put("content", eventText);
        object.put("text", eventText);
        object.put("event", eventText);

        object.put("source", username);
        object.put("username", username);

        JSONArray embeds = new JSONArray();
        JSONObject discordEmbed = new JSONObject();
        discordEmbed.put("title", headerTitle);

        discordEmbed.put("description", String.format("**Event**: %s\n**Path**: %s\n**Type**: %s",normalChangeType, fullPath, typeOfObject));

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