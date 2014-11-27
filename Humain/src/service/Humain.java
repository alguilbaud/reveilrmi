package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class Humain extends UnicastRemoteObject implements IHumain{

	private Etat etat;
	private IReveil reveil;
	
	private int temps = 0; //temps ecoule, equivaut a la date
	private int prochainReveilSpontanne = Integer.MAX_VALUE; //contient la date du prochain reveil spontanne qui est prevu, MAX_VALUE si aucun
	private LinkedList<Integer> prochainesSonneries = new LinkedList<Integer>(); //contient les dates d'arrivée de toutes les sonneries qui ont été envoyées par le reveil mais qui sont encore en transit, vide si aucune
	
	private int dateMinArmement = 168;
	private int dateMaxArmement = 276;
	
	enum Etat {
		Reveille, Endormi, Intermidiaire
	};
	
	public Humain(IReveil reveil) throws RemoteException {
		this.etat = Etat.Reveille;
		this.reveil = reveil;
	}
	
	public void seCoucher(){
		this.etat=Etat.Endormi;
		//on calcule le prochain reveil spontanne, qui est une valeur aleatoire entre 12 et 144
		prochainReveilSpontanne = temps + 12 + (int) Math.floor(132 * Math.random());
		System.out.println("temps = " + temps + " : L'humain va se coucher");
	}
	
	public void reveilSpontanne()
	{
		this.etat=Etat.Intermidiaire;
		prochainReveilSpontanne = Integer.MAX_VALUE;
		System.out.println("temps = " + temps + " : Reveil spontanne de l'humain");
	}
	
	public void entendSonnerie(){
		this.etat=Etat.Intermidiaire;
		prochainReveilSpontanne = Integer.MAX_VALUE;
		System.out.println("temps = " + temps + " : La sonnerie retentit");
	}
	
	public void seRendort(){
		  this.etat=Etat.Endormi;
		  prochainReveilSpontanne = temps + 12 + (int) Math.floor(132 * Math.random());
		  System.out.println("temps = " + temps + " : L'humain se rendort");
	}
	
	public void seReveille(){
		  this.etat=Etat.Reveille;
		  dateMinArmement = temps + 168;
		  dateMaxArmement = temps + 276;
		  System.out.println("temps = " + temps + " : L'humain se reveille");
	}
	
	public void sonne() throws RemoteException{
		int delai = (int) Math.floor(3 * Math.random());
		System.out.println("temps = " + temps + " : Reveil envoie sonnerie, temps de transit : " + delai);
		prochainesSonneries.add(temps + delai);
	}
	
	//fonction qui est appelee pour proposer une nouvelle date, retourne cette date et met temps a jour si aucun evenement prevu avant, sinon retourne date du prochain evenement
	public int avancerTemps(int nouveauTemps) throws RemoteException {
		int dateProchainEvenement = prochainReveilSpontanne;
		for (int i : prochainesSonneries){
			dateProchainEvenement = Math.min(dateProchainEvenement, i);
		}
		
		if(nouveauTemps < temps){
			System.out.println("Erreur, nouveau temps (" + nouveauTemps + ") inferieur a temps de l'humain (" + temps + ")");
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
		return (prochainesSonneries.size() == 0) && (prochainReveilSpontanne > temps);
	}
}
