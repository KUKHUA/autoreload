package misc;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GetEventJSON {
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