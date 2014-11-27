package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Scanner;

public class Humain extends UnicastRemoteObject implements IHumain{

	private Etat etat;
	private IReveil reveil;
	
	private int temps = 0; //temps ecoule, equivaut a la date
	private int prochainReveilSpontanne = Integer.MAX_VALUE; //contient la date du prochain reveil spontanne qui est prevu, MAX_VALUE si aucun
	private LinkedList<Integer> prochainesSonneries = new LinkedList<Integer>(); //contient les dates d'arrivée de toutes les sonneries qui ont été envoyées par le reveil mais qui sont encore en transit, vide si aucune
	
	private int dateMinArmement = 168;
	private int dateMaxArmement = 276;
	
	enum Etat {
		Reveille, Endormi, Intermediaire
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
		this.etat=Etat.Intermediaire;
		prochainReveilSpontanne = Integer.MAX_VALUE;
		System.out.println("temps = " + temps + " : Reveil spontanne de l'humain");
	}
	
	public void entendSonnerie(){
		if (etat == Etat.Endormi){
			this.etat=Etat.Intermediaire;
			prochainReveilSpontanne = Integer.MAX_VALUE;
			System.out.println("temps = " + temps + " : La sonnerie retentit");
		}
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
		boolean pret = (prochainReveilSpontanne > temps) && (dateMaxArmement > temps);
		for (int i : prochainesSonneries){
			pret = pret && (i > temps);
		}
		return pret;
	}
	
	public void comportement(){
		Scanner scan;
		String saisie;
		while (true){
			int dateProchaineSonnerie = Integer.MAX_VALUE;
			for (int i : prochainesSonneries){
				dateProchaineSonnerie = Math.min(dateProchaineSonnerie, i);
			}
			if(temps == dateProchaineSonnerie){
				prochainesSonneries.remove(temps);
				entendSonnerie();
			}
			else if (etat == Etat.Reveille){
				System.out.println();
				System.out.println("Date (temps) actuelle : " + temps);
				System.out.println("Date minimum pour armer le réveil : " + dateMinArmement);
				System.out.println("Date maximum pour armer le réveil : " + dateMaxArmement);
				System.out.println();
				System.out.println("Actions possibles :");
				if (dateMaxArmement > temps){
					System.out.println("Avancer dans le temps (Taper 1)");
				}
				if (dateMinArmement <= temps){
					System.out.println("Armer le reveil et se coucher (Taper 2)");
				}
				System.out.println();
				scan = new Scanner(System.in);
				saisie = scan.nextLine();
				int type = Integer.parseInt(saisie);
				if ((type == 1) && (dateMaxArmement > temps)){
					System.out.println("Avancer de combien de temps ? (Entrer un entier)");
					saisie = scan.nextLine();
					int duree = Integer.parseInt(saisie);
					int nouvelleDate = temps + duree;
					//si on a des sonneries à entendre avant, on prendre la date de la prochaine sonnerie
					for(int i : prochainesSonneries){
						nouvelleDate = Math.min(nouvelleDate, i);
					}
					//si dateMaxArmement est inférieure, la nouvelle date vaut dateMaxArmement
					if(dateMaxArmement < nouvelleDate){
						nouvelleDate = dateMaxArmement;
					}
					try {
						temps = reveil.avancerTemps(nouvelleDate);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if ((type == 2) && (dateMinArmement <= temps)){
					try {
						reveil.arme();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					seCoucher();
				}
				else{
					System.out.println("Erreur : mauvaise saisie (soit n'existe pas, soit indisponible)");
				}
				scan.close();
			}		
			else if (etat == Etat.Intermediaire){
				System.out.println();
				System.out.println("Date (temps) actuelle : " + temps);
				System.out.println("L'humain est dans l'etat intermediaire.");
				System.out.println("Actions possibles :");
				System.out.println("Se recoucher (Taper 1)");
				System.out.println("Se reveiller (Taper 2)");
				scan = new Scanner(System.in);
				saisie = scan.nextLine();
				int type = Integer.parseInt(saisie);
				if(type == 1){
					seRendort();
				}
				else if (type == 2){
					seReveille();
				}
				else{
					System.out.println("Erreur : mauvaise saisie (1 ou 2 uniquement)");
				}
			}
			else if ((etat == Etat.Endormi) && (prochainReveilSpontanne == temps)){
				reveilSpontanne();
			}
			else if (estPret()){
				int dateProchainEvenement = prochainReveilSpontanne;
				for (int i : prochainesSonneries){
					dateProchainEvenement = Math.min(dateProchainEvenement, i);
				}
				try {
					temps = reveil.avancerTemps(dateProchainEvenement);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
