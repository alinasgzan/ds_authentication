package rmiprintserver.models;

import rmiprintserver.Constants;

import java.util.HashMap;

public class User {

    private String ip;
    private String username;
    private boolean isActive;
    private HashMap<String, Boolean> permissions;


    public User(String _ip, String _username) {
        isActive = false;
        permissions = Constants.defaultPermissions;
        ip = _ip;
        username = _username;
    }

    public User(String _ip, String _username, HashMap<String, Boolean> _permissions) {
        isActive = false;
        permissions = _permissions;
        ip = _ip;
        username = _username;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void toggleActive() {
        isActive = !isActive;
    }

    public boolean compareTo(String _username, String _ip) {
        return username.equals(_username) && ip.equals(_ip);
    }

    public boolean isAllowed(String permission) {
        return permissions.get(permission);
    }
}
