package service;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IReveil extends Remote {
	
	public void enregistrerHumain(String adresseHumain) throws RemoteException;
	public void arme() throws RemoteException;
	public void desarme() throws RemoteException;
	public int avancerTemps(int nouveauTemps) throws RemoteException;
	
}
