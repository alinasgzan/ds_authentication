package rmiprintserver;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServant extends UnicastRemoteObject implements IPrintServer, IPrintAuth {

		public PrintServant() throws RemoteException {
			super();
		}
	
		@Override
		public String start() throws RemoteException {
			return "Server started.. ";
		}


		@Override
		public String stop() throws RemoteException {
			return "Server stopped";
		}

		@Override
		public String print(String filename, String printer) throws RemoteException {
			return "Printing file " + filename + " from printer " + printer;
		}



		@Override
		public String queue() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public String topQueue(int job) throws RemoteException {
			return "moving job " + job + " to top";
		}



		@Override
		public String restart() throws RemoteException {
			return "restarting server.. ";
		}



		@Override
		public String status() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public String readConfig(String parameter) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public String setConfig(String parameter, String value) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String login(String userName, String password) {
            return "";
		}

		@Override
		public boolean checkSessionValidity(String token) {
			return true;
		}

		@Override
		public String getPublicKey() {
			return "";
		}

}

    