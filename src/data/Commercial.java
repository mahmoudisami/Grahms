package data;

public class Commercial extends District{

	private int size;
	private String img = "/src/img/ComLv1.png";

	public Commercial() {
		super(Const.GAIN_COM,0); 
		size = 1; // Taille de depart
	}
	
	public String getImg() {
		return img;
	}
	
	public void upSize() {
		size++;
	}
	
	
}
