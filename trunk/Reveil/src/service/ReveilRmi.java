package service;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ReveilRmi {

	public static void main(String[] args) {
		/*// TODO Auto-generated method stub
       try {
//    	    LocateRegistry.createRegistry(1099);
//		    Reveil reveil= new Reveil();
//		    Naming.rebind("rmi://localhost:1099/reveil", reveil);
		    IHumain  stub= (IHumain)Naming.lookup("rmi://localhost:1098/humain");
			stub.entendSonnerie();
			
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		*/
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
        try {
        	LocateRegistry.createRegistry(8000);
        	Reveil reveil = new Reveil();
			Naming.rebind("rmi://localhost:8000/sujet", reveil);
			IHumain  stub= (IHumain)Naming.lookup("rmi://localhost:1098/humain");
			stub.entendSonnerie();
            //IReveil stubReveil = (IReveil) UnicastRemoteObject.exportObject(reveil, 0);
			//Registry registry = LocateRegistry.getRegistry();
			//registry.rebind("Serveur", stubReveil);
			//System.out.println("Chat bound");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
