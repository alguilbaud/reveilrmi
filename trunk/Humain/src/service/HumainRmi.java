package service;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class HumainRmi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         try {
//			IReveil  stub= (IReveil)Naming.lookup("rmi://localhost:1099/reveil");
//			stub.armee();
//			stub.deSarmee();
//			
			LocateRegistry.createRegistry(1098);
		    Humain humain= new Humain();
		    Naming.rebind("rmi://localhost:1098/humain", humain);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
