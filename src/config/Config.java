package config;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

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