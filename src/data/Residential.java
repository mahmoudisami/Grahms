package data;

public class Residential extends District{

	private int size;
	private final static int GAIN = 100; // Gain par semaine ou par mois � d�finir
	private int maxPeople;
	
	public Residential() {
		super(GAIN,0); 
		size = 1; // Taille de d�part
	}
}
