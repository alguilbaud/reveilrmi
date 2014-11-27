package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHumain extends Remote{

	public void sonne() throws RemoteException; 
	public int avancerTemps(int nouveauTemps) throws RemoteException;
}
