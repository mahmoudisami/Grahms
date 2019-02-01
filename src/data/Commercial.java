package data;

public class Commercial extends District{

	private int size;
	
	public Commercial() {
		super(Const.GAIN_COM,0); 
		size = 1; // Taille de départ
	}
}
