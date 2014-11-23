package service;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IReveil extends Remote {
	
	public void armee() throws RemoteException;
	public void deSarmee() throws RemoteException;
	
}
