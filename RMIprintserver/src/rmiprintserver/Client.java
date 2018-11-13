package rmiprintserver;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    private static IPrintServer printService;

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, UnknownHostException {

        setSettings();

        Registry registry = LocateRegistry.getRegistry(
                InetAddress.getLocalHost().getHostName(), 5099,
                new SslRMIClientSocketFactory());

        printService = (IPrintServer) registry.lookup("printserver");

        Scanner sc = new Scanner(System.in);
        String command = "";
        String username = "";
        boolean isAuthentificated = false;

        while (true) {
            command = sc.nextLine();

            if (isAuthentificated) {
                //Handle logged state with valid token

                if (command.equals("close") || command.equals("logout")) {
                    System.out.println("goodbye..");
                    handleUserActions("logout", username);
                    break;
                }
                String response = handleUserActions(command, username);
                System.out.println(response);
            } else {
                try {

                    isAuthentificated = handleAuthentification(command);

                    if (isAuthentificated) {
                        username = command.split("\\s+")[0];
                    }
                } catch (RemoteException e) {
                }
                //Handle invalid/unlogged state
            }
        }
        sc.close();
    }

    /**
     * @param input
     * @return
     */
    private static String handleUserActions(String input, String username) {
        String[] commands = input.split("\\s+");
        try {
            switch (commands[0]) {
                case ("start"): {
                    return printService.start(username);
                }
                case ("stop"): {
                    return printService.stop(username);
                }
                case ("print"): {
                    return printService.print(commands[1], commands[2], username);
                }
                case ("queue"): {
                    return printService.queue(username);
                }
                case ("top"): {
                    return printService.topQueue(Integer.valueOf(commands[1]), username);
                }
                case ("restart"): {
                    return printService.restart(username);
                }
                case ("status"): {
                    return printService.status(username);
                }
                case ("setconfig"): {

                    return printService.setConfig(commands[1], commands[2], username);

                }
                case ("readconfig"): {
                    return printService.readConfig(commands[1], username);
                }
                case ("logout"): {
                    if(printService.logout(username))
                        return "You have logged out";
                    else return "There was a problem";
                }

            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return "No such command: " + input;
    }

    private static boolean handleAuthentification(String input) throws RemoteException {
        String[] commands = input.split("\\s+");

        //User input
        var username = commands[0];
        var password = commands[1];


        return printService.login(username, password);
    }
    private static void setSettings() {

        String pass = "potato";

        System.setProperty("javax.net.ssl.debug", "all");

        System.out.println(String.format("%s/RMI\\ print\\ server/SSL/keystore-Client", getProjectFolder()));
        System.out.println(String.format("%s/RMI\\ print\\ server/SSL/truststore-Client", getProjectFolder()));

        System.setProperty("javax.net.ssl.keyStore", String.format("%s/RMIprintserver/src/SSL/keystore-Client.jks", getProjectFolder()));
        System.setProperty("javax.net.ssl.keyStorePassword", "orange");
        System.setProperty("javax.net.ssl.trustStore", String.format("%s/RMIprintserver/src/SSL/truststore-Client.jks", getProjectFolder()));
        System.setProperty("javax.net.ssl.trustStorePassword", "clementine");
    }

    /**
     * Kinda dodgy but works
     * @return
     */
    private static String getProjectFolder() {
        return System.getProperty("user.dir");
    }
}

