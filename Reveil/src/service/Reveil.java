package service;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Reveil extends UnicastRemoteObject implements IReveil{
	
	private IHumain humain;
	
	private int temps = 0; //temps logique ecoule, equivaut a la date 
	private int prochaineSonnerie = Integer.MAX_VALUE; //quand le reveil doit attendre pour sonner, on stocke la date a laquelle il va sonner, MAX_VALUE quand aucune sonnerie en attente
	private int prochainArmement = Integer.MAX_VALUE; //date d'arrivee du prochain armement qui a ete envoye mais est encore en transit, MAX_VALUE quand aucun
	private int prochainDesarmement = Integer.MAX_VALUE; //date d'arrivee du prochain desarmement qui a ete envoye mais est encore en transit, MAX_VALUE quand aucun
	
	


	public Reveil() throws RemoteException{
		humain = null;
	}
	
	public void enregistrerHumain(String adresseHumain) throws RemoteException{
		try {
			humain = (IHumain)Naming.lookup(adresseHumain);
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comportement();
	}
	
	@Override
	public void arme() throws RemoteException //methode appelee par l'humain pour armer la sonnerie
	{   
		int delai = (int) Math.floor(3*Math.random());
		System.out.println("temps = " + temps + " : Humain demande a armer reveil, temps de transit : "+ delai);
		prochainArmement = temps + delai;
	}
	
	private void armerSonnerie(){ //methode pour armer reellement le réveil
		prochaineSonnerie = temps + 96; //prochaine sonnerie prevue 8h plus tard
		System.out.println("temps = " + temps + " : Reveil arme");
	}
	
	@Override
	public void desarme() throws RemoteException
	{    
		int delai = (int) Math.floor(3*Math.random());
		System.out.println("temps = " + temps + " : Humain demande à desarmer reveil, temps de transit : "+ delai);
		prochainArmement = temps + delai;
	}
	
	private void desarmerSonnerie(){
		prochaineSonnerie = Integer.MAX_VALUE;
	    System.out.println("temps = " + temps + " : Reveil desarme");
	}

	//fonction qui est appelee pour proposer une nouvelle date, retourne cette date et met temps a jour si aucun evenement prevu avant, sinon retourne date du prochain evenement
	public int avancerTemps(int nouveauTemps) throws RemoteException { 
		int dateProchainEvenement = Math.min(prochaineSonnerie, Math.min(prochainArmement, prochainDesarmement));
		if(nouveauTemps < temps){
			System.out.println("Erreur, nouveau temps (" + nouveauTemps + ") inferieur a temps du reveil (" + temps + ")");
		}
		else if(dateProchainEvenement < nouveauTemps){
			temps = dateProchainEvenement;
		}
		else{
			temps = nouveauTemps;
		}
		return temps;
	}
	
	public boolean estPret(){ //true s'il n'y a plus d'evenements devant s'actionner a la date temps, false s'il en reste
		return (Math.min(prochaineSonnerie, Math.min(prochainArmement, prochainDesarmement))) > temps;
	}
	
	public void comportement(){ //methode qui coordonne les actions a faire a chaque date et qui propose d'avancer dans le temps quand il n'y a plus rien a faire
		while(true){
			if (prochaineSonnerie == temps){
				try {
					humain.sonne();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				prochaineSonnerie += 1; //prochaine sonnerie dans 5 minutes
			}
			if (prochainDesarmement == temps){
				desarmerSonnerie();
				prochainDesarmement = Integer.MAX_VALUE;
			}
			if (prochainArmement == temps){
				armerSonnerie();
				prochainArmement = Integer.MAX_VALUE;
			}
			if (estPret()){
				try {
					temps = humain.avancerTemps(Math.min(prochaineSonnerie, Math.min(prochainArmement, prochainDesarmement)));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
}
