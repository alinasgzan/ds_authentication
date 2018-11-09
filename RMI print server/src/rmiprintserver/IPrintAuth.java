package rmiprintserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrintAuth extends Remote {

    //TODO : authentication method
    String login(String username, String password) throws RemoteException;
    boolean checkSessionValidity(String token) throws RemoteException;
    String getPublicKey() throws RemoteException;

}
