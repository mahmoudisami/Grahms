package data;

public class Commercial extends District{

	private int taille;
	private final static int GAIN = 500; // Gain par semaine ou par mois � d�finir
	private final static int COST = 0; // Gratuit � construire
	
	public Commercial() {
		super(GAIN,COST); 
		taille = 1; // Taille de d�part
	}
}
