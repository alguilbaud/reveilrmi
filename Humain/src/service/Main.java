package service;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         try {
        	IReveil reveil = (IReveil)Naming.lookup("rmi://localhost:8000/reveil");
		    Humain humain= new Humain(reveil); 
		    LocateRegistry.createRegistry(1098);
		    Naming.rebind("rmi://localhost:1098/humain", humain);
		    reveil.enregistrerHumain("rmi://localhost:1098/humain");
		    humain.comportement();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
