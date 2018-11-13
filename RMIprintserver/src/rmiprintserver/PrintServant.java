package rmiprintserver;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;


public class PrintServant extends UnicastRemoteObject implements IPrintServer {

    private ClientManager cm;
    private PrinterManager pm;
    private Logger logger = Logger.getLogger("Logger");

    private static final String passwordFile = "RMIprintserver/userspas.txt";

    public PrintServant() throws RemoteException {
        super();
        cm = new ClientManager();
        pm = new PrinterManager();
    }

    @Override
    public String start(String username) {
        if (!IsValidUser(username))
            {
            logger.info("invalid username, operation denied");
            return "Unauthorized";}
        if (!pm.getIsActive()) {
            pm.toggleIsActive();
            logger.info("user " + username + " started server");
            return "Server started.. ";
        }
        logger.info("cannot start server, already started");
        return "Server is already started.";

    }


    @Override
    public String stop(String username) {
        if (!IsValidUser(username))
            {
            logger.info("invalid username, operation denied");
            return "Unauthorized";}
        if (!pm.getIsActive()) {
            pm.toggleIsActive();
            logger.info("user " + username + " stopped server");
            return "Server stopped";
        }
        logger.info("cannot stop server, already stoped");
        return "Server is already stopped.";
    }

    @Override
    public String print(String filename, String printer, String username) {
        if (!IsValidUser(username)) {
            logger.info("invalid username, operation denied");
            return "Unauthorized";
        }

        if (!pm.getIsActive()) return Constants.NOT_STARTED_MESSAGE;
        logger.info("service not started, operation denied");

        try {
            pm.print(filename, Integer.parseInt(printer), username);
        } catch (NotActiveException e) {
            return String.format("Printer %s is not active", printer);
        } catch (Exception e) {
            return String.format("Printer %s does not exist", printer);
        }
        logger.info("Printing file " + filename + " from printer " + printer + " for user " + username );
        return "Printing file " + filename + " from printer " + printer;
    }

    @Override
    public String queue(String username) {
        if (!IsValidUser(username))
        {
            logger.info("invalid username, operation denied");
            return "Unauthorized";
        }

        if (!pm.getIsActive()) return Constants.NOT_STARTED_MESSAGE;

        logger.info("displaying queue for user " + username );
        return pm.queue();
    }


    @Override
    public String topQueue(int job, String username) {
        if (!IsValidUser(username))
        {
            logger.info("invalid username, operation denied");
            return "Unauthorized";
        }

        if (!pm.getIsActive()) return Constants.NOT_STARTED_MESSAGE;

        if (pm.topQueue(job))
            return "moving job " + job + " to top";
        else
            return String.format("No such job with id: %d", job);
    }

    @Override
    public String restart(String username) {
        if (!IsValidUser(username))
        {
            logger.info("invalid username, operation denied");
            return "Unauthorized";
        }

        if (!pm.getIsActive()) return Constants.NOT_STARTED_MESSAGE;

        if (pm.getIsActive()) {
            pm.toggleIsActive();
            pm.toggleIsActive();
            return "Restarting server.. ";
        }
        return "Cannot restart a stopped server";
    }

    @Override
    public String status(String username) {
        if (!IsValidUser(username))
        {
            logger.info("invalid username, operation denied");
            return "Unauthorized";
        }

        if (!pm.getIsActive()) return Constants.NOT_STARTED_MESSAGE;

        return pm.status();
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {

        if (IsStringNullOrEmptyOrWhiteSpace(username) || IsStringNullOrEmptyOrWhiteSpace(password)) return false;

        try {
            if (cm.IsUserBlocked(username, getClientHost())){
                logger.info("User is blocked, login not allowed");
                return  false;}
        } catch (ServerNotActiveException e) {
            return false;
        }

        boolean isValid = userCheck(username, password);
        try {
            if (isValid) {

                cm.RegisterClient(username, getClientHost());
                return true;
            }
        cm.RegisterIntruderHitCount(username, getClientHost());

        } catch (ServerNotActiveException e) {
        }
        return false;
    }

    private static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private boolean userCheck(String username, String password) throws RemoteException{
        // I assume username and password are already decrypted
        boolean checkApproved = false;
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(passwordFile);
            br = new BufferedReader(fr);

            String currentEntry;
            br.readLine(); // first line is irrelevant
            while ((currentEntry = br.readLine()) != null) {
                String[] fields = currentEntry.split("\\s+");
                if (fields[0].equals(username)){
                    String hashedCorrectPassword = fields[1];
                    String salt = fields[2];

                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    md.update(password.getBytes("UTF8"));
                    md.update(salt.getBytes("UTF8"));
                    byte[] digest = md.digest();
                    String hashedCandidatePassword = byteArrayToHex(digest).toLowerCase();

                    if (hashedCandidatePassword.equals(hashedCorrectPassword)) {
                        logger.info("authentication successful, user " + username + " logged in");
                        checkApproved = true; }
                    else {
                        logger.info("username and pass do not match, access denied");
                    }
                    break;
                }
                else {
                        logger.info("no such user: " + username);
                }
            }
        }

        catch (FileNotFoundException e){
            // someone moved/renamed password file, send altert
        }
        catch (IOException e){
            // log
        }
        catch (NoSuchAlgorithmException e) {}

        return checkApproved;
    }

    @Override
    public boolean logout(String username) {
        try {
            cm.UnregisterClient(username, getClientHost());
            return true;
        } catch (ServerNotActiveException e) {
        }

        return false;
    }



    @Override
    public String readConfig(String parameter, String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String setConfig(String parameter, String value, String username) {
        // TODO Auto-generated method stub
        return null;
    }

    private static boolean IsStringNullOrEmptyOrWhiteSpace(String input) {
        return (input == null || input.equals("") || input.equals(" "));
    }

    private boolean IsValidUser(String username) {
        try {
            return !IsStringNullOrEmptyOrWhiteSpace(username) && cm.IsUserRegistered(username, getClientHost());
        } catch (Exception e) {
            return false;
        }
    }
}

    