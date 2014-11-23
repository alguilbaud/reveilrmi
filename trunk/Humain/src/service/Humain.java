package service;
public class Humain {

	private String etat;
	enum Etat {
		Reveille, Endormi, Intermidiaire
	};
	public Humain() {
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
	public void rntendSonnerie()
	{
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
		Humain test = new Humain();
		try {
			System.out.println(test.getEtat());
			new Thread().sleep((long) 960.00);
			test.seCoucher();
			System.out.println(test.getEtat());
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
		}

	}
	
}
