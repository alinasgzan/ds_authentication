package rmiprintserver;

import rmiprintserver.models.User;

import java.util.ArrayList;

public class ClientManager {
    private ArrayList<User> clientList;

    public ClientManager() {
        clientList = new ArrayList<>();
    }

    /**
     * Registers client
     *
     * @param username
     * @param ip
     */
    public void RegisterClient(String username, String ip) {
        clientList.add(new User(ip, username));
    }

    /**
     * Unregisters Client
     *
     * @param username
     * @param ip
     */
    public void UnregisterClient(String username, String ip) {
        clientList.remove(new User(ip, username));
    }

    /**
     * Iterates through the existing users and tries to toggle active status
     *
     * @param username
     * @param ip
     */
    public void toggleActiveClient(String username, String ip) {
        //If we use standard iterator pattern we won't be able to update
        for (int i = 0; i <= clientList.size(); i++) {
            //Get a local copy of the entity
            User _user = clientList.get(i);
            if (_user.getUsername().equals(username) && _user.getIp().equals(ip)) {
                //Update status
                _user.toggleActive();
                //Update collection
                clientList.set(i, _user);
                //Stop iterating
                break;
            }
        }
    }

    public boolean IsUserRegistered(String username, String ip) {
        for (int i = 0; i <= clientList.size(); i++) {
            if (clientList.get(i).compareTo(username, ip))
                return true;
        }
        return false;
    }
}
