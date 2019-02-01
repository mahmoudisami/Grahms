package data;

public class Commercial extends District{

	private int size;
	private final static int GAIN = 500; // Gain par semaine ou par mois à définir
	private final static int COST = 0; // Gratuit à construire
	
	public Commercial() {
		super(GAIN,COST); 
		size = 1; // Taille de départ
	}
}
