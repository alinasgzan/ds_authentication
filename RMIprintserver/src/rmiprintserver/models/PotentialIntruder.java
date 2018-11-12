package rmiprintserver.models;

public class PotentialIntruder {

    private String username;
    private String ip;
    private int numberOfTries;
    private boolean blocked;

    public PotentialIntruder(String _username, String _ip) {
        username = _username;
        ip = _ip;
        numberOfTries = 0;
        blocked = false;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public void hit() {
        numberOfTries++;
    }

    public int getHitCount() {
        return numberOfTries;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void block() {
        blocked = true;
    }
}
