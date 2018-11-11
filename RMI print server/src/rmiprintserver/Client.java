package rmiprintserver;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    private static IPrintServer printService;

	    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {

	        printService = (IPrintServer) Naming.lookup("rmi://localhost:5099/printserver");

	        Scanner sc = new Scanner(System.in);
	        String command = "";
	        String username = "";
	        boolean isAuthentificated = false;

	        while(true) {
				command = sc.nextLine();

				if (isAuthentificated) {
					//Handle logged state with valid token

					if(command.equals("close")) {
						System.out.println("goodbye..");
						break;
					}
					String response = handleUserActions(command, username);
					System.out.println(response);
				}
				else {
				    try {

                        isAuthentificated = handleAuthentification(command);
                    } catch (RemoteException e) {
                    }
					//Handle invalid/unlogged state
				}
			}
	        sc.close();
	    }

	/**
	 *
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
	                case ("top") : {
	                	return printService.topQueue(Integer.valueOf(commands[1]), username);
	                  }
	                case ("restart") : {
	                    return printService.restart(username);
	                }
	                case("status") : {
	                    return printService.status(username);
	                }
	                case ("setconfig") : {
	                    
	                	return printService.setConfig(commands[1], commands[2], username);
	                    
	                }
	                case ("readconfig") : {
	                    return printService.readConfig(commands[1], username);
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
            var  username = commands[0];
            var password = commands[1];


            return printService.login(username, password);
	    }
}

