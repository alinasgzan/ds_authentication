package rmiprintserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class AccessManager {

    public static enum setRelations{
         Subset, Superset
    }

    private static final String roleTransactionsFile = "RMIprintserver/roleTransactions.txt";
    private static final String userRolesFile = "RMIprintserver/userRoles.txt";
    private static final ArrayList<String> allRoles = new ArrayList<>();
    private static HashMap<String, ArrayList<String>> roleTransactions = new HashMap<>();
    public static boolean checkSetInclusion = false;

    public static HashMap<String, ArrayList<String>> readRoleTransactions() {

        HashMap<String, ArrayList<String>> hm = new HashMap<>();
        try {
            FileReader fr = new FileReader(roleTransactionsFile);
            BufferedReader br = new BufferedReader(fr);
            String entry = "";

            br.readLine();
            String[] setInclusionArr =  br.readLine().split("=");
            if (setInclusionArr[0].equals("checkSetInclusion")){
                if (setInclusionArr[1].equals("true")) checkSetInclusion = true;
            }

            while ((entry = br.readLine()) != null) {
                String[] preProcessed = entry.split(":");
                String role = preProcessed[0];
                allRoles.add(role);
                String[] transactions = preProcessed[1].split(" ");
                hm.put(role,new ArrayList<>(Arrays.asList(transactions)));
            }
        } catch (IOException e) {
            //Some issue with the file
            roleTransactions = hm;
            return hm;
        }
        roleTransactions = hm;
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

    public static HashMap<String, setRelations> CalcRoleSetRelations(){
        HashMap<String,setRelations> hm = new HashMap<>();
        for (int i = 0; i<allRoles.size(); i++){
            for (int j = 0; j<allRoles.size(); j++){
                if (!allRoles.get(i).equals(allRoles.get(j))
                        && !hm.containsKey(allRoles.get(i)+","+allRoles.get(j))){
                    ArrayList<String> opsRoleI = roleTransactions.get(allRoles.get(i));
                    ArrayList<String> opsRoleJ = roleTransactions.get(allRoles.get(j));
                    if (opsRoleI.containsAll(opsRoleJ)){
                        hm.put((allRoles.get(j)+","+allRoles.get(i)), setRelations.Subset);
                        hm.put((allRoles.get(i)+","+allRoles.get(j)), setRelations.Superset);
                    }
                    else if (opsRoleJ.containsAll(opsRoleI)){
                        hm.put((allRoles.get(i)+","+allRoles.get(j)), setRelations.Subset);
                        hm.put((allRoles.get(j)+","+allRoles.get(i)), setRelations.Superset);
                    }
                    else {
                        // shall None value entry for the key be created then?..
                    }
                }
            }
        }
        return hm;
    }
}