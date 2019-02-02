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
	
	public int getCost() {
		return super.getCost();
	}
	
	public int getGain() {
		return super.getGain();
	}
	
	public int getMaintenanceCost() {
		return super.getMaintenanceCost(); //Aucun frai de maintenance
	}
}
