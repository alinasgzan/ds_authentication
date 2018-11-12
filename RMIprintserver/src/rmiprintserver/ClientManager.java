package rmiprintserver;

import rmiprintserver.models.PotentialIntruder;
import rmiprintserver.models.User;

import java.util.ArrayList;

public class ClientManager {
    private ArrayList<User> clientList;
    private ArrayList<PotentialIntruder> intruderList;

    public ClientManager() {
        clientList = new ArrayList<>();
        intruderList = new ArrayList<>();
    }

    /**
     * Registers client
     *
     * @param username
     * @param ip
     */
    public void RegisterClient(String username, String ip) {
        clientList.add(new User(ip, username));

        UnregisterPotentialIntruder(username, ip);
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

    private void RegisterPotentialIntruder(String username, String ip) {
        intruderList.add(new PotentialIntruder(username, ip));
    }

    public void UnregisterPotentialIntruder(String username, String ip) {
        intruderList.remove(new PotentialIntruder(username, ip));
    }

    public void RegisterIntruderHitCount(String username, String ip) {
        //If we use standard iterator pattern we won't be able to update
        for (int i = 0; i <= intruderList.size(); i++) {
            //Get a local copy of the entity
            PotentialIntruder _intruder = intruderList.get(i);
            if (_intruder.getUsername().equals(username) && _intruder.getIp().equals(ip)) {
                //Update status
                if (_intruder.getHitCount() > randomWithRange(5,10)){
                    _intruder.block();

                    intruderList.set(i, _intruder);

                    break;
                }
                _intruder.hit();
                //Update collection
                intruderList.set(i, _intruder);
                //Stop iterating
                break;
            }
        }
        RegisterPotentialIntruder(username, ip);

        RegisterIntruderHitCount(username, ip);
    }

    public boolean IsUserBlocked(String username, String ip) {
        //If we use standard iterator pattern we won't be able to update
        for (int i = 0; i <= intruderList.size(); i++) {
            //Get a local copy of the entity
            PotentialIntruder _intruder = intruderList.get(i);
            if (_intruder.getUsername().equals(username) && _intruder.getIp().equals(ip)) {
                //Update status
               return _intruder.isBlocked();
            }
        }
        return false;
    }

    private static int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
