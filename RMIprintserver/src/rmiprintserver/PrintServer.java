package rmiprintserver;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrintServer {

    public static void main(String args[]) throws RemoteException {
        setSettings();
        // testing
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

        String pass = "potato";

        System.setProperty("javax.net.ssl.debug", "all");
        System.out.println(String.format("%s/RMI\\ print\\ server/SSL/keystore-Server", getProjectFolder()));
        System.out.println(String.format("%s/RMI\\ print\\ server/SSL/truststore-Server.jks", getProjectFolder()));
        System.setProperty("javax.net.ssl.keyStore", String.format("%s/RMIprintserver/src/SSL/keystore-Server.jks", getProjectFolder()));
        System.setProperty("javax.net.ssl.keyStorePassword", "potato");
        System.setProperty("javax.net.ssl.trustStore", String.format("%s/RMIprintserver/src/SSL/truststore-Server.jks", getProjectFolder()));
        System.setProperty("javax.net.ssl.trustStorePassword", "banana");
    }

    /**
     * Kinda dodgy but works
     * @return
     */
    private static String getProjectFolder() {
        return System.getProperty("user.dir");
    }
}
