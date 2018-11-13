package rmiprintserver;

import rmiprintserver.models.Config;

import java.io.*;

public class ConfigManager {
    //TODO:Implement this
    //TODO: Implement writing to a file
    Config config;

    public ConfigManager() {
        config = new Config();
    }

    public void AddConfigEntry(String key, String entry){
        config.addConfigEntry(key,entry);
    }

    public void RemoveConfigEntry(String key) {
        config.removeConfigEntry(key);
    }

    public void EditConfigEntry(String key, String entry) {
        config.editConfigEntry(key, entry);
    }

    public void EmptyConfig() {
        config.resetConfig();
    }

    /**
     * Something for next version
     * Current version will keep it simple
     */
    private void populateConfig() {
        String fileContent = "";
        File _configFile = new File("config.txt");
        try {
            _configFile.createNewFile(); // if file already exists will do nothing
            BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(
                                                new BufferedInputStream(
                                                        new FileInputStream(_configFile))));
            String _line;
            while((_line = reader.readLine()) != null) {
                //Should follow the convention
                //Key,Entry,
                fileContent += _line;
            }

            config.populateConfig(fileContent);

        } catch (IOException e) {
            //Error creating new file
        }
    }

}
