package rmiprintserver;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrintServer extends Remote {

	//Generic methods required by the assignment paper
	String start() throws RemoteException;
	String stop() throws RemoteException;
	String print(String filename, String printer) throws RemoteException;
	String queue() throws RemoteException;
	String topQueue(int job) throws RemoteException;
	String restart() throws RemoteException;
	String status() throws RemoteException;
	String readConfig(String parameter) throws RemoteException;
	String setConfig(String parameter, String value) throws RemoteException;

}
