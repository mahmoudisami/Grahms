package moteur;

public class Services extends District{

	private People people;
	private int taille;
	private final static int MAINTENANCE_COST = 100; // Cout chaque semaine/mois � d�finir
	private final static int GAIN = 500; // Gain par semaine ou par mois � d�finir
	private final static int COST = 300; // Cout de construction
	
	public Services() {
		super(GAIN,COST);
		people = new People();
		
	}
}
