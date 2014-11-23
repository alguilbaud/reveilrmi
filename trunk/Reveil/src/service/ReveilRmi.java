package service;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ReveilRmi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       try {
    	    LocateRegistry.createRegistry(1099);
		    Reveil reveil= new Reveil();
		    Naming.rebind("rmi://localhost:1099/reveil", reveil);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
