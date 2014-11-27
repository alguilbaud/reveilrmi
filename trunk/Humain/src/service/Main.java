package service;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         try {
//			IReveil  stub= (IReveil)Naming.lookup("rmi://localhost:1099/reveil");
//			stub.armee();
//			stub.deSarmee();
        	IReveil reveil = (IReveil)Naming.lookup("rmi://localhost:8000/reveil");
		    Humain humain= new Humain(reveil);
		    Naming.rebind("rmi://localhost:1098/humain", humain);
		    LocateRegistry.createRegistry(1098);
		    reveil.enregistrerHumain("rmi://localhost:1098/humain");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
