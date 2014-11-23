package service;
import java.rmi.RemoteException;



import java.rmi.server.UnicastRemoteObject;

public class Reveil extends UnicastRemoteObject implements IReveil{

	private String etat;
	enum Etat {
		EstArme, Attend, EstDesarme
	};

	public Reveil()  throws RemoteException{
		this.etat = Etat.EstDesarme.toString();
	}
	@Override
	public void armee() throws RemoteException
	{    		
	     this.etat=Etat.EstArme.toString();
	     System.out.println("je suis armé");
	}
	public void sonne() 
	{    		
	     this.etat=Etat.Attend.toString();
	}
	@Override
	public void deSarmee() throws RemoteException
	{    		
	     this.etat=Etat.EstDesarme.toString();
	     System.out.println("je suis désarmé");
	}
	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public static void main(String[] args) {
		
//		try {
//			Reveil test = new Reveil();
//			System.out.println(test.getEtat());
//			test.armee();
//			System.out.println(test.getEtat());
//			new Thread().sleep((long) 960.00);
//			System.out.println(test.getEtat());
//			test.sonne();
//			new Thread().sleep((long)1.0);
//			System.out.println(test.getEtat());
//			test.deSarmee();
//			System.out.println(test.getEtat());
//			System.out.println(test.getEtat());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.err.println("je sonne pas");
//		}
	
	}


}
