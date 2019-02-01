package data;

public class Residential extends District{

	private int size;
	private int maxPeople;
	private String img1 = "/src/img/residLv1.png";
	private String img2 = "/src/img/residLv2.png";
	private String img3 = "/src/img/residLv3.png";
	
	public Residential() {
		super(Const.GAIN_RES,0); 
		size = 1; // Taille de depart
	}
	
	public String getImg() {
		return super.getImg(size, img1, img2, img3);
	}
	
	public void upSize() {
		size++;
	}
	
	public int getMaxPeople() {
		return maxPeople;
	}
}
