package data;

public class Residential extends District{

	private int size;
	private int maxPeople;
	
	public Residential() {
		super(Const.GAIN_RES,0); 
		size = 1; // Taille de départ
	}
}
