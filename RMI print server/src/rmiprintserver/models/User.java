package rmiprintserver.models;

public class User {

    private String ip;
    private String username;
    private boolean isActive;

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
}
