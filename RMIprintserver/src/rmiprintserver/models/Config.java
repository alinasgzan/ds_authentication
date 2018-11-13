package rmiprintserver.models;

import java.util.HashMap;

public class Config {

    private HashMap<String, String> configData;

    public Config() {
        configData = new HashMap<>();
    }

    public void populateConfig(String fileStream) {
        String[] data = fileStream.split(",");

        //Data length should always be even
        for(int i = 0; i < data.length; i+=2){
            configData.put(data[i], data[i+1]);
        }
    }

    public void addConfigEntry(String key, String value) {
        configData.put(key, value);
    }

    public void removeConfigEntry(String key) {
        configData.remove(key);
    }

    public void editConfigEntry(String key, String value) {
        try {
            configData.replace(key, value);
        } catch(Exception e) {
            //Key not found
        }
    }

    @Override
    public String toString() {

        String[] keySet = (String[]) configData.keySet().toArray();

        String result = "";
        for(int i = 0; i < configData.size(); i+=2){
            result += String.format("%s : %s%n",keySet[i], configData.get(keySet[i]));
        }
        return result;
    }

    public void resetConfig() {
        configData = new HashMap<>();
    }

}