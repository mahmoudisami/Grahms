package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Services extends District{

	private int size;
	private String src = "src/image/Services.png";
	private String srcStation = "src/image/ServicesStation.png"; 
	private BufferedImage img;
	 
	public Services() {
		super(Const.COST_SERV);
		size = 1; // Taille de depart
		actualPeople = 0;
	}
	
	public BufferedImage getImg() {
		try {
			if(!isStation) {
				img = ImageIO.read(new File(src));
			}
			else {
				img = ImageIO.read(new File(srcStation)); // changer img avec station
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		return img;
	}
	
	public void upSize() {
		size++;
	}
	
	public int getMaintenanceCost() {
		return Const.MAINTENANCE_COST_SERV;
	}
	
	public boolean isService() {
		return true;
	}
}
