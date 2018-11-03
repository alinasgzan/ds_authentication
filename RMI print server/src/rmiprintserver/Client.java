package rmiprintserver;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
	private static PrintService service;
	    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
	        
	        service = (PrintService) Naming.lookup("rmi://localhost:5099/printserver");
	        
	        Scanner sc = new Scanner(System.in);
	        String command;
	        System.out.println("enter print command.. ");
	        while(true) {
	            command = sc.nextLine();
	            if(command.equals("close")) {
	                 System.out.println("goodbye..");
	                break;
	            }
	            String response = handle(command);
	                System.out.println(response);
	        }
	        sc.close();
	    }

	    private static String handle(String input) {
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

}

