package rmiprintserver.models;

import java.util.ArrayList;

public class User {

    private String ip;
    private String username;
    private boolean isActive;
    private ArrayList<String> roles;

    public User(String _ip, String _username) {
        isActive = false;
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
}
