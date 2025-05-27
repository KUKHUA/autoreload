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

package misc;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The {@code GetEventJSON} class is responsible for generating a JSONObject
 * that represents a file system event. This JSON object is compatible with various platforms like Discord embeds, and Slack blocks.
 */

public class GetEventJSON {
    /**
     * Constructs a JSONObject detailing a file system event.
     *
     * The method takes information about a file system change (type of change, path)
     * and formats it into a comprehensive JSON object. This object includes:
     * <ul>
     *   <li>General event information (content, text, source, type, timestamp).</li>
     *   <li>A "data" section with raw event details (fullPath, original changeType, normalized changeType).</li>
     *   <li>A "embeds" array specifically for Discord.</li>
     *   <li>A "blocks" array formatted for Slack.</li>
     * </ul>
     *
     * The method attempts to resolve the provided {@code path} to its real path. If this
     * fails, the original {@code fullPath} string is used.
     * It utilizes helper classes {@code ParseChange} to normalize the change type string
     * and {@code FileFilter} to determine if the path refers to a file or directory.
     *
     * @param changeType A string representing the type of file system change
     *                   (e.g., "ENTRY_CREATE", "ENTRY_MODIFY", "ENTRY_DELETE").
     * @param fullPath   The initial string representation of the full path to the
     *                   file or directory that changed. This may be updated by
     *                   resolving {@code path} to its real path.
     * @param path       A {@link java.nio.file.Path} object representing the file or
     *                   directory that underwent the change.
     * @return A {@link org.json.JSONObject} containing structured information about the
     *         file system event, suitable for various notification consumers.
     */
    public static JSONObject run(String changeType, String fullPath, Path path){
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

        object.put("type", typeOfObject + "." + normalChangeType);
        object.put("time", java.time.ZonedDateTime.now().format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        JSONArray embeds = new JSONArray();
        JSONObject discordEmbed = new JSONObject();
        discordEmbed.put("title", headerTitle);

        discordEmbed.put("description", String.format("**Event**: %s\n**Path**: %s\n**Type**: %s", normalChangeType, fullPath, typeOfObject));

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

        return object;
    }
}