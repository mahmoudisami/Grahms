package data;

public class Services extends District{

	private int size;
	private String img1 = "/src/img/ServLv1.png";
	private String img2 = "/src/img/ServLv2.png";
	private String img3 = "/src/img/ServLv3.png";
	 
	public Services() {
		super(Const.GAIN_SERV,Const.COST_SERV);
		size = 1; // Taille de départ
	}
	
	public String getImg() {
		if(size == 1) {
			return img1;
		}
		else if(size == 2) {
			return img2;
		}
		else {
			return img3;
		}
	}
	
	public void upSize() {
		size++;
	}
}
