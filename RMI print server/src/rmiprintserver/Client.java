package rmiprintserver;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    private static IPrintServer service;

	    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {

	        service = (IPrintServer) Naming.lookup("rmi://localhost:5099/printserver");
	        //Using standard JWT tokens
			var token = "";

	        Scanner sc = new Scanner(System.in);
	        String command = "";
	        System.out.println("enter print command.. ");

	        while(true) {
	        	if (token != "" && service.checkSessionValidity(token)) {
					//Handle logged state with valid token

					command = sc.nextLine();
					if(command.equals("close")) {
						System.out.println("goodbye..");
						break;
					}
					String response = handleInput(command);
					System.out.println(response);
				}
				else {
				    try {

                        token = handleAuthentification(command);
                    } catch (RemoteException e) {
				        token = "";
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
	    private static String handleInput(String input) {
	        String[] commands = input.split("\\s+");
	        try {
	            switch (commands[0]) {
	                case ("start"): {
	                	return service.start();
	                }
	                case ("stop"): {
	                	return service.stop();
	                }
	                case ("print"): {
	                	return service.print(commands[1], commands[2]);
	                }
	                case ("queue"): {
	                    return service.queue();
	                }
	                case ("top") : {
	                	return service.topQueue(Integer.valueOf(commands[1]));
	                  }
	                case ("restart") : {
	                    return service.restart();
	                }
	                case("status") : {
	                    return service.status();
	                }
	                case ("setconfig") : {
	                    
	                	return service.setConfig(commands[1], commands[2]);
	                    
	                }
	                case ("readconfig") : {
	                    return service.readConfig(commands[1]);
	                }
	                
	            }
	        } catch (Exception e) {
	            System.out.println(e.toString());
	            e.printStackTrace();
	        }

	        return "No such command: " + input;
	    }

        private static String handleAuthentification(String input) throws RemoteException {
            String[] commands = input.split("\\s+");

            //User input
            var  username = commands[0];
            var password = commands[1];

            //Server params
            var serverPublicKey = service.getPublicKey();


            return service.login(username, encryptPassword(password, serverPublicKey));
        }

        private static String encryptPassword(String password, String publicKey) {

	        return "";
        }

}

