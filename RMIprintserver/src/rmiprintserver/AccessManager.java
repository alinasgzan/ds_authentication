package rmiprintserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class AccessManager {

    private static final String roleTransactionsFile = "RMIprintserver/roleTransactions.txt";
    private static final String userRolesFile = "RMIprintserver/userRoles.txt";

    public static HashMap<String, ArrayList<String>> readRoleTransactions() {

        HashMap<String, ArrayList<String>> hm = new HashMap<>();
        try {
            FileReader fr = new FileReader(roleTransactionsFile);
            BufferedReader br = new BufferedReader(fr);
            String entry = "";
            br.readLine();
            while ((entry = br.readLine()) != null) {
                String[] preProcessed = entry.split(":");
                String role = preProcessed[0];
                String[] transactions = preProcessed[1].split(" ");
                hm.put(role,new ArrayList<>(Arrays.asList(transactions)));
            }
        } catch (IOException e) {
            //Some issue with the file
            return hm;
        }
        //Cuz java
        return hm;
    }

    public static ArrayList<String> getRolesOfUser(String username){
        ArrayList<String> roleList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(userRolesFile);
            BufferedReader br = new BufferedReader(fr);
            String entry = "";
            br.readLine();
            while ((entry = br.readLine()) != null) {
                String[] preProcessed = entry.split(":");
                if (preProcessed[0].equals(username)){
                    roleList = new ArrayList<>(Arrays.asList(preProcessed[1].split(" ")));
                }
            }

        } catch (IOException e) {
            return roleList;
        }
        return roleList;
    }
}