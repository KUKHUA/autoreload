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
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

/**
 * The {@code Config} class provides a singleton instance to manage application configuration.
 * It allows reading and writing properties from a configuration file named ".changedetecter.properties".
 * The configuration file is created in the current working directory if it does not exist.
 * The class ensures that the configuration is loaded only once and provides methods to access and modify the properties.  
 *  The save method persists any changes made to the properties back to the configuration file. It is not automatically saved after each change, allowing for batch updates.
 */
public final class Config {
    private static Config instance;
    private final String configName = ".changedetecter.properties";
    private boolean isReady = false;
    private Properties propertiesInstance;

    private Config(){}

    public static Config instance(){
        if(instance == null) instance = new Config();
        return instance;
    }

    private void initConfig(){
        this.isReady = true;
        final String fullPropertiesPath = System.getProperty("user.dir") + File.separator + configName;

        this.propertiesInstance = new Properties();
        File configFile = new File(fullPropertiesPath);

        try {
             if (!configFile.exists()) configFile.createNewFile();
        } catch (Exception e){
            throw new RuntimeException("Unable to create file.\n" + e);
        }

        try (FileInputStream fis = new FileInputStream(fullPropertiesPath)) {
            propertiesInstance.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load configuration file.\n" + e);
        }

    }

    public boolean isReady(){
        return this.isReady;
    }

    public String get(String key){
        if(!isReady) this.initConfig();
        return propertiesInstance.getProperty(key);
    }

    public String getDefault(String key, String defaultValue){
        if(!isReady) this.initConfig();
        return propertiesInstance.getProperty(key,defaultValue);
    }

    public Object set(String key, String value){
        if(!isReady) this.initConfig();
        return propertiesInstance.setProperty(key,value);
    }

    public void save(){
        if (!isReady) this.initConfig();
        final String fullPropertiesPath = System.getProperty("user.dir") + File.separator + configName;

        try (FileOutputStream out = new FileOutputStream(fullPropertiesPath)) {
            propertiesInstance.store(out, "Updated config");
        } catch (Exception e) {
            throw new RuntimeException("Unable to save configuration file.\n" + e);
        }
    }

}