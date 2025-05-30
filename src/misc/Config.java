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
import org.json.JSONTokener;

import java.io.*;

/**
 * The {@code Config} class provides a singleton instance to manage application configuration.
 * The configuration file is created in the current working directory if it does not exist.
 *  The save method persists any changes made to the properties back to the configuration file. It is not automatically saved after each change, allowing for batch updates.
 */
public final class Config {
    private final String configFileName = "changedetector.json";
    private JSONObject config;
    private File configFile;
    private static Config instance;

    private Config(){
        this.configFile = new File(configFileName);
        this.load();
    }

    public static Config instance(){
        if(instance == null) instance = new Config();
        return instance;
    }

    public String getDefault(String key, String defaultValue){
        return config.optString(key,defaultValue);
    }

    public void set(String key, String value){
        config.put(key, value);
    }

    public JSONObject object(){
        return config;
    }

    public void save(){
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(config.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(){
        if (!configFile.exists()){
            config = new JSONObject();
            this.save();
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JSONTokener tokener = new JSONTokener(reader);
            config = new JSONObject(tokener);
        } catch (IOException e) {
            e.printStackTrace();
            config = new JSONObject();
        }
    }
}