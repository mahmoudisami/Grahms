package data;

public class Commercial extends District{

	private int size;
	private String img1 = "/src/img/ComLv1.png";
	private String img2 = "/src/img/ComLv2.png";
	private String img3 = "/src/img/ComLv3.png";
	
	public Commercial() {
		super(Const.GAIN_COM,0); 
		size = 1; // Taille de depart
	}
	
	public String getImg() {
		return super.getImg(size, img1, img2, img3);
	}
	
	public void upSize() {
		size++;
	}
}
