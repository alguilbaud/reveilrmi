package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHumain extends Remote{

	public void entendSonnerie() throws RemoteException;
}
