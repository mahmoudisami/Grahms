package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Services extends District{

	private int size;
	private String src = "src/image/Services.jpg";
	private BufferedImage img;
	 
	public Services() {
		super(Const.GAIN_SERV,Const.COST_SERV);
		size = 1; // Taille de depart 
	}
	
	public BufferedImage getImg() {
		try {
			img = ImageIO.read(new File(src));
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
}
