package service;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
		}
		try {
			LocateRegistry.createRegistry(8000);
			Reveil reveil = new Reveil();
            Naming.rebind("rmi://localhost:8000/reveil", reveil);
            //IHumain  stub= (IHumain)Naming.lookup("rmi://localhost:1098/humain");
    } catch (Exception e) {
            e.printStackTrace();
    }

	}

}
