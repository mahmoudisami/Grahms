package moteur;

public class Residential extends District{

	private People people;
	private int taille;
	private final static int GAIN = 100; // Gain par semaine ou par mois à définir
	
	public Residential() {
		super(GAIN,0);
		people = new People();
		taille = 1; // Taille de départ
	}
}
