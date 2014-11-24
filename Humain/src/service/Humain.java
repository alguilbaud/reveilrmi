package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Humain extends UnicastRemoteObject implements IHumain{

	private String etat;
	enum Etat {
		Reveille, Endormi, Intermidiaire
	};
	public Humain() throws RemoteException {
		this.etat = Etat.Reveille.toString();
	}
	public void seCoucher()
	{
		  this.etat=Etat.Endormi.toString();
	}
	public void reveilSpontanne()
	{
		  this.etat=Etat.Intermidiaire.toString();
		  
	}
	public void entendSonnerie()
	{System.out.println("hiho");
		  this.etat=Etat.Intermidiaire.toString();
	}
	public void seRendort()
	{
		  this.etat=Etat.Endormi.toString();
	}
	public void desarmeReveil()
	{
		  this.etat=Etat.Reveille.toString();
	}
	
	
	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	public static void main(String[] args) {
	;
		try {
			Humain test = new Humain();
			System.out.println(test.getEtat());
			new Thread().sleep((long) 960.00);
			test.seCoucher();
			System.out.println(test.getEtat());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}

	}
	
}
