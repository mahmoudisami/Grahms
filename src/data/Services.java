package data;

public class Services extends District{

	private int size;
	private final static int MAINTENANCE_COST = 100; // Cout chaque semaine/mois à définir
	private final static int GAIN = 500; // Gain par semaine ou par mois à définir
	private final static int COST = 300; // Cout de construction
	 
	public Services() {
		super(GAIN,COST);
		size = 1; // Taille de départ
	}
}
