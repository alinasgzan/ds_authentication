package rmiprintserver;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrintServer {

    public static void main(String args[]) throws RemoteException {
        setSettings();

        try {

            setSettings();

            Registry registry = LocateRegistry.createRegistry(5099, new SslRMIClientSocketFactory(), new SslRMIServerSocketFactory(null, null, true));
            System.out.println("RMI registry running on port " + 5099);

            registry.bind("printserver", new PrintServant());

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void setSettings() {

        String pass = "password";

        System.setProperty("javax.net.ssl.debug", "all");

        System.setProperty("javax.net.ssl.keyStore", "C:\\ssl\\serverkeystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", pass);
        System.setProperty("javax.net.ssl.trustStore", "C:\\ssl\\servertruststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", pass);
    }

    /**
     * Kinda dodgy but works
     * @return
     */
    private static String getProjectFolder() {
        return System.getProperty("user.dir");
    }
}
