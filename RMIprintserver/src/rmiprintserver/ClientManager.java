package rmiprintserver;

import rmiprintserver.models.PotentialIntruder;
import rmiprintserver.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ClientManager {
    private ArrayList<User> clientList;
    private ArrayList<PotentialIntruder> intruderList;
    private HashMap<String, ArrayList<String>> roleTransactions;

    public ClientManager() {
        clientList = new ArrayList<>();
        intruderList = new ArrayList<>();
        roleTransactions = AccessManager.readRoleTransactions();
    }

    /**
     * Registers client
     *
     * @param username
     * @param ip
     */
    public void RegisterClient(String username, String ip) {
        clientList.add(new User(ip, username, AccessManager.getRolesOfUser(username)));

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
        for (int i = 0; i < clientList.size(); i++) {
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
        for (int i = 0; i < clientList.size(); i++) {
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
        boolean isUserFound = false;
        //If we use standard iterator pattern we won't be able to update
        for (int i = 0; i < intruderList.size(); i++) {
            //Get a local copy of the entity
            PotentialIntruder _intruder = intruderList.get(i);
            if (_intruder.getUsername().equals(username) && _intruder.getIp().equals(ip)) {
                //Update status
                if (_intruder.getHitCount() > randomWithRange(5,10)){
                    System.out.println("found intruder " + _intruder.getUsername() + " after " + _intruder.getHitCount() + " hits");
                    _intruder.block();

                    intruderList.set(i, _intruder);
                    isUserFound = true;
                    break;
                }
                _intruder.hit();
                //Update collection
                intruderList.set(i, _intruder);
                //Stop iterating
                isUserFound = true;
                break;
            }
        }
        if (!isUserFound) {

        RegisterPotentialIntruder(username, ip);

        RegisterIntruderHitCount(username, ip);
        }
    }

    public boolean IsUserBlocked(String username, String ip) {
        //If we use standard iterator pattern we won't be able to update
        for (int i = 0; i < intruderList.size(); i++) {
            //Get a local copy of the entity
            PotentialIntruder _intruder = intruderList.get(i);
            if (_intruder.getUsername().equals(username) && _intruder.getIp().equals(ip)) {
                //Update status
               return _intruder.isBlocked();
            }
        }
        return false;
    }

    public boolean IsRoleAllowedForUser(String username, String role){
        ArrayList<String> rolesForUser = AccessManager.getRolesOfUser(username);
        System.out.println("Debugging IsRoleAllowedForUser");
        System.out.println(username);
        System.out.println(role);
        System.out.println(Arrays.toString(rolesForUser.toArray()));

        if (rolesForUser.contains(role)) return true;
        return false;
    }

    public boolean IsTransactionllowedForRole(String role, String transaction) {
        System.out.println("Debugging IsTransactionllowedForRole");
        System.out.println(role);
        System.out.println(transaction);
        System.out.println(Arrays.toString(roleTransactions.get(role).toArray()));
        if (roleTransactions.get(role).contains(transaction)) return true;
        return false;
    }


    private static int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
