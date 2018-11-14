package rmiprintserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AccessManager {

    private static final String accessPolicyFile = "RMIprintserver/accessPolicy.txt";

    public static HashMap<String, Boolean> getPermissionsForUser(String username) {

        HashMap<String, Boolean> hm = Constants.defaultPermissions;
        try {
            FileReader fr = new FileReader(accessPolicyFile);
            BufferedReader br = new BufferedReader(fr);
            String entry = "";
            while ((entry = br.readLine()) != null) {
                String[] permissions = entry.split(" ");

                if (permissions[0].equals(username)) {
                    for (int i = 1; i < permissions.length; i++) {
                        hm.replace(permissions[i], true);
                    }

                    return hm;
                }
            }
        } catch (IOException e) {
            //Some issue with the file
            return hm;
        }
        //Cuz java
        return hm;
    }
}
