package rmiprintserver;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class PrintServant extends UnicastRemoteObject implements IPrintServer {

        private ClientManager cm;
        private PrinterManager pm;

        private static final String passwordFile = "RMI print server/userspas.txt";

		public PrintServant() throws RemoteException {
			super();
			cm = new ClientManager();
			pm = new PrinterManager();
		}

		@Override
		public String start(String username) throws RemoteException {

		    if (!pm.getIsActive())
            {
                pm.toggleIsActive();
			    return "Server started.. ";
            } else {
		        return "Server is already started.";
            }
		}


		@Override
		public String stop(String username) throws RemoteException {
			if (pm.getIsActive()) {
                pm.toggleIsActive();
            }
		    return "Server stopped";
		}

		@Override
		public String print(String filename, String printer, String username) throws RemoteException {
			return "Printing file " + filename + " from printer " + printer;
		}



		@Override
		public String queue(String username) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public String topQueue(int job, String username) throws RemoteException {
			return "moving job " + job + " to top";
		}



		@Override
		public String restart(String username) throws RemoteException {
			return "restarting server.. ";
		}



		@Override
		public String status(String username) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public String readConfig(String parameter, String username) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public String setConfig(String parameter, String value, String username) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean login(String username, String password) throws RemoteException {
		    boolean isValid = userCheck(username, password);
            try {
                if (isValid) {
                    cm.RegisterClient(username, getClientHost());
                    return true;
                }
            } catch(ServerNotActiveException e) {}

			return false;
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
						String hashedCandidatePassword = DatatypeConverter.printHexBinary(digest).toLowerCase();

						if (hashedCandidatePassword.equals(hashedCorrectPassword)) checkApproved = true;
						break;
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
		public boolean logout(String username) throws RemoteException {
			try {
                cm.UnregisterClient(username, getClientHost());
                return true;
            } catch (ServerNotActiveException e) {}
            finally {
			    return false;
            }
		}
}

    