package rmiprintserver;

import java.io.NotActiveException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServant extends UnicastRemoteObject implements IPrintServer {

    private ClientManager cm;
    private PrinterManager pm;

    public PrintServant() throws RemoteException {
        super();
        cm = new ClientManager();
        pm = new PrinterManager();
    }

    @Override
    public String start(String username) {

        if (!pm.getIsActive()) {
            pm.toggleIsActive();
            return "Server started.. ";
        }
        return "Server is already started.";
    }


    @Override
    public String stop(String username) {
        if (pm.getIsActive()) {
            pm.toggleIsActive();

            return "Server stopped";
        }
        return "Server is already stopped.";
    }

    @Override
    public String print(String filename, String printer, String username) {
        try {
            pm.print(filename, Integer.parseInt(printer), username);
        } catch (NotActiveException e) {
            return String.format("Printer %s is not active", printer);
        } catch (Exception e) {
            return String.format("Printer %s does not exist", printer);
        }
        return "Printing file " + filename + " from printer " + printer;
    }

    @Override
    public String queue(String username) {
        return pm.queue();
    }


    @Override
    public String topQueue(int job, String username) {

        if (pm.topQueue(job))
            return "moving job " + job + " to top";
        else
            return String.format("No such job with id: %d", job);
    }

    @Override
    public String restart(String username) {
        if (pm.getIsActive()) {
            pm.toggleIsActive();
            pm.toggleIsActive();
            return "Restarting server.. ";
        }
        return "Cannot restart a stopped server";
    }

    @Override
    public String status(String username) {
        return pm.status();
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {

        boolean isValid = userCheck(username, password);
        try {
            if (isValid) {
                cm.RegisterClient(username, getClientHost());
                return true;
            }
        } catch (ServerNotActiveException e) {
        }

        return false;
    }

    private boolean userCheck(String username, String password) {
        //TODO: Connect to password storage and check
        return true;
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
}

    