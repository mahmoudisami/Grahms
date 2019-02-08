package data;

public class Services extends District{

	private int size;
	private String img = "/src/img/ServLv1.png";
	 
	public Services() {
		super(Const.GAIN_SERV,Const.COST_SERV);
		size = 1; // Taille de depart
	}
	
	public String getImg() {
		return img;
	}
	
	public void upSize() {
		size++;
	}
	
	public int getMaintenanceCost() {
		return Const.MAINTENANCE_COST_SERV;
	}
}
